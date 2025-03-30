package com.hockeymanager.application.engine.services;

import com.hockeymanager.application.engine.models.GameResult;
import com.hockeymanager.application.players.models.Player;
import com.hockeymanager.application.players.repositories.PlayersRepository;
import com.hockeymanager.application.teams.models.Team;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import java.util.List;
import java.util.Random;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
public class GamesEngineService {
    private PlayersRepository playersRepository;
    private static final Random random = new Random();

    public GameResult simulateGame(Team homeTeam, Team awayTeam) {
        List<Player> homeTeamPlayers = playersRepository.findAllByTeamId(homeTeam.getId());
        List<Player> awayTeamPlayers = playersRepository.findAllByTeamId(awayTeam.getId());

        double homeTeamOverall = homeTeamPlayers.stream().map(player -> player.getCurrentAbility()).reduce(0,
                (currentTotal, currentAbility) -> currentTotal + currentAbility) / homeTeamPlayers.size();
        double awayTeamOverall = awayTeamPlayers.stream().map(player -> player.getCurrentAbility()).reduce(0,
                (currentTotal, currentAbility) -> currentTotal + currentAbility) / awayTeamPlayers.size();

        double seasonAverageGoalsPerGamePerTeam = 3.12;

        double strengthRatioHome = homeTeamOverall / (homeTeamOverall + awayTeamOverall);
        double strengthRatioAway = 1 - strengthRatioHome;

        double meanHome = seasonAverageGoalsPerGamePerTeam * 2 * strengthRatioHome;
        double meanAway = seasonAverageGoalsPerGamePerTeam * 2 * strengthRatioAway;

        double stdDev = 1.5; // Ajustable pour plus ou moins de variance

        int homeScore = (int) Math.max(0, Math.round(random.nextGaussian() * stdDev + meanHome));
        int awayScore = (int) Math.max(0, Math.round(random.nextGaussian() * stdDev + meanAway));

        if (homeScore == awayScore) {
            boolean homeWins = random.nextDouble() < strengthRatioHome;
            if (homeWins) {
                homeScore++;
            } else {
                awayScore++;
            }
        }

        return new GameResult(homeScore, awayScore);
    }
}
