package com.hockeymanager.application.integration.engine.services;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.hockeymanager.application.engine.models.GameResult;
import com.hockeymanager.application.engine.services.GamesEngineService;
import com.hockeymanager.application.schedules.services.SchedulesService;
import com.hockeymanager.application.teams.models.Team;
import com.hockeymanager.application.teams.repositories.TeamsRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GamesEngineServiceIntegrationTests {
    @Autowired
    private GamesEngineService gamesEngineService;

    @Autowired
    private TeamsRepository teamsRepository;

    @Autowired
    private SchedulesService schedulesService;

    @Test
    void simulateGameWhenLeafsAndHabsShouldReturnValidScore() {
        // Arrange
        var teams = teamsRepository.findAll();
        var leafs = teams.stream().filter(team -> team.getName().equals("Toronto Maple Leafs")).findFirst()
                .orElseThrow();
        var habs = teams.stream().filter(team -> team.getName().equals("Montréal Canadiens")).findFirst().orElseThrow();

        // Act
        GameResult gameResult = gamesEngineService.simulateGame(leafs, habs);

        // Assert
        assertNotEquals(gameResult.getHomeScore(), gameResult.getAwayScore());
    }

    @Getter
    @Setter
    @NoArgsConstructor
    private class Ranking {
        private int victories = 0;
        private int defeats = 0;
        private int goalsFor = 0;
        private int goalsAgainst = 0;
    }

    @Test
    @Disabled("This test is used to simulate a season and benchmark the simulation.")
    void simulate1000Games() {
        var teams = teamsRepository.findAll();
        var schedule = schedulesService.generateSchedule(100, teams, 2023);

        List<GameResult> gameResults = new ArrayList<>();
        Map<String, Ranking> teamVictories = new HashMap<>();
        long startTime = System.currentTimeMillis();

        for (int i = 0; i < schedule.size(); i++) {
            // Arrange
            Team homeTeam = schedule.get(i).getHomeTeam();
            Team awayTeam = schedule.get(i).getAwayTeam();

            // Act
            GameResult gameResult = gamesEngineService.simulateGame(homeTeam, awayTeam);
            if (!teamVictories.containsKey(homeTeam.getName())) {
                teamVictories.put(homeTeam.getName(), new Ranking());
            }
            if (!teamVictories.containsKey(awayTeam.getName())) {
                teamVictories.put(awayTeam.getName(), new Ranking());
            }
            if (gameResult.getHomeScore() > gameResult.getAwayScore()) {
                var homeTeamRanking = teamVictories.get(homeTeam.getName());
                homeTeamRanking.setVictories(homeTeamRanking.getVictories() + 1);
                homeTeamRanking.setGoalsFor(homeTeamRanking.getGoalsFor() + gameResult.getHomeScore());
                homeTeamRanking.setGoalsAgainst(homeTeamRanking.getGoalsAgainst() + gameResult.getAwayScore());
                teamVictories.put(homeTeam.getName(), homeTeamRanking);

                var awayTeamRanking = teamVictories.get(awayTeam.getName());
                awayTeamRanking.setDefeats(awayTeamRanking.getDefeats() + 1);
                awayTeamRanking.setGoalsFor(awayTeamRanking.getGoalsFor() + gameResult.getAwayScore());
                awayTeamRanking.setGoalsAgainst(awayTeamRanking.getGoalsAgainst() + gameResult.getHomeScore());
                teamVictories.put(awayTeam.getName(), awayTeamRanking);
            } else {
                var awayTeamRanking = teamVictories.get(awayTeam.getName());
                awayTeamRanking.setVictories(awayTeamRanking.getVictories() + 1);
                awayTeamRanking.setGoalsFor(awayTeamRanking.getGoalsFor() + gameResult.getAwayScore());
                awayTeamRanking.setGoalsAgainst(awayTeamRanking.getGoalsAgainst() + gameResult.getHomeScore());
                teamVictories.put(awayTeam.getName(), awayTeamRanking);

                var homeTeamRanking = teamVictories.get(homeTeam.getName());
                homeTeamRanking.setDefeats(homeTeamRanking.getDefeats() + 1);
                homeTeamRanking.setGoalsFor(homeTeamRanking.getGoalsFor() + gameResult.getHomeScore());
                homeTeamRanking.setGoalsAgainst(homeTeamRanking.getGoalsAgainst() + gameResult.getAwayScore());
                teamVictories.put(homeTeam.getName(), homeTeamRanking);
            }
            gameResults.add(gameResult);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time to simulate 1312 games: " + (endTime - startTime) + "ms");

        // Assert
        var biggestScore = gameResults.stream().mapToInt(GameResult::getHomeScore).max().orElseThrow();
        var biggestScore2 = gameResults.stream().mapToInt(GameResult::getAwayScore).max().orElseThrow();
        var biggestDifference = gameResults.stream()
                .mapToInt(gameResult -> Math.abs(gameResult.getHomeScore() - gameResult.getAwayScore())).max()
                .orElseThrow();

        System.out.println("Biggest difference: " + biggestDifference);
        System.out.println(
                "Biggest score: " + (biggestScore > biggestScore2 ? biggestScore : biggestScore2));
        teamVictories.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().getVictories() - e1.getValue().getVictories())
                .forEach(entry -> System.out
                        .println(entry.getKey() + ": " + entry.getValue().getVictories() + " victories, "
                                + entry.getValue().getDefeats() + " defeats, " + entry.getValue().getGoalsFor()
                                + " goals for, " + entry.getValue().getGoalsAgainst() + " goals against"));
    }
}
