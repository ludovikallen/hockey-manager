package com.hockeymanager.application.integration.engine.services;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.hockeymanager.application.engine.models.GameResult;
import com.hockeymanager.application.engine.services.GamesEngineService;
import com.hockeymanager.application.teams.models.Team;
import com.hockeymanager.application.teams.repositories.TeamsRepository;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestReporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GamesEngineServiceIntegrationTests {
    @Autowired
    private GamesEngineService gamesEngineService;

    @Autowired
    private TeamsRepository teamsRepository;

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
    @Disabled("Only for performance testing purposes")
    void simulate1000Games(TestReporter testReporter) {
        List<GameResult> gameResults = new ArrayList<>();
        Map<String, Ranking> teamVictories = new HashMap<>();
        var teams = teamsRepository.findAll();
        // Mesure time to simulate
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < 1312; i++) {
            // Arrange
            Team randomHomeTeam;
            Team randomAwayTeam;
            do {
                randomHomeTeam = teams.get((int) (Math.random() * teams.size()));
                randomAwayTeam = teams.get((int) (Math.random() * teams.size()));
            } while (randomHomeTeam.getId() == randomAwayTeam.getId());

            // Act
            GameResult gameResult = gamesEngineService.simulateGame(randomHomeTeam, randomAwayTeam);
            if (!teamVictories.containsKey(randomHomeTeam.getName())) {
                teamVictories.put(randomHomeTeam.getName(), new Ranking());
            }
            if (!teamVictories.containsKey(randomAwayTeam.getName())) {
                teamVictories.put(randomAwayTeam.getName(), new Ranking());
            }
            if (gameResult.getHomeScore() > gameResult.getAwayScore()) {
                var homeTeamRanking = teamVictories.get(randomHomeTeam.getName());
                homeTeamRanking.setVictories(homeTeamRanking.getVictories() + 1);
                homeTeamRanking.setGoalsFor(homeTeamRanking.getGoalsFor() + gameResult.getHomeScore());
                homeTeamRanking.setGoalsAgainst(homeTeamRanking.getGoalsAgainst() + gameResult.getAwayScore());
                teamVictories.put(randomHomeTeam.getName(), homeTeamRanking);

                var awayTeamRanking = teamVictories.get(randomAwayTeam.getName());
                awayTeamRanking.setDefeats(awayTeamRanking.getDefeats() + 1);
                awayTeamRanking.setGoalsFor(awayTeamRanking.getGoalsFor() + gameResult.getAwayScore());
                awayTeamRanking.setGoalsAgainst(awayTeamRanking.getGoalsAgainst() + gameResult.getHomeScore());
                teamVictories.put(randomAwayTeam.getName(), awayTeamRanking);
            } else {
                var awayTeamRanking = teamVictories.get(randomAwayTeam.getName());
                awayTeamRanking.setVictories(awayTeamRanking.getVictories() + 1);
                awayTeamRanking.setGoalsFor(awayTeamRanking.getGoalsFor() + gameResult.getAwayScore());
                awayTeamRanking.setGoalsAgainst(awayTeamRanking.getGoalsAgainst() + gameResult.getHomeScore());
                teamVictories.put(randomAwayTeam.getName(), awayTeamRanking);

                var homeTeamRanking = teamVictories.get(randomHomeTeam.getName());
                homeTeamRanking.setDefeats(homeTeamRanking.getDefeats() + 1);
                homeTeamRanking.setGoalsFor(homeTeamRanking.getGoalsFor() + gameResult.getHomeScore());
                homeTeamRanking.setGoalsAgainst(homeTeamRanking.getGoalsAgainst() + gameResult.getAwayScore());
                teamVictories.put(randomHomeTeam.getName(), homeTeamRanking);
            }
            gameResults.add(gameResult);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time to simulate 1312 games: " + (endTime - startTime) + "ms");

        // Assert
        var biggestScore = gameResults.stream().mapToInt(GameResult::getHomeScore).max().orElseThrow();
        var smallestScore = gameResults.stream().mapToInt(GameResult::getHomeScore).min().orElseThrow();
        var biggestScore2 = gameResults.stream().mapToInt(GameResult::getAwayScore).max().orElseThrow();
        var smallestScore2 = gameResults.stream().mapToInt(GameResult::getAwayScore).min().orElseThrow();
        var biggestDifference = gameResults.stream()
                .mapToInt(gameResult -> Math.abs(gameResult.getHomeScore() - gameResult.getAwayScore())).max()
                .orElseThrow();

        System.out.println("Biggest difference: " + biggestDifference);
        System.out.println(
                "Biggest score: " + (biggestScore > biggestScore2 ? biggestScore : biggestScore2));
        testReporter
                .publishEntry("small",
                        "Smallest score: " + (smallestScore < smallestScore2 ? smallestScore : smallestScore2));
        teamVictories.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().getVictories() - e1.getValue().getVictories())
                .forEach(entry -> System.out
                        .println(entry.getKey() + ": " + entry.getValue().getVictories() + " victories, "
                                + entry.getValue().getDefeats() + " defeats, " + entry.getValue().getGoalsFor()
                                + " goals for, " + entry.getValue().getGoalsAgainst() + " goals against"));
    }
}
