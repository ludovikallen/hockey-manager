package com.hockeymanager.application.engine.services;

import com.hockeymanager.application.dynasties.repositories.DynastiesRepository;
import com.hockeymanager.application.schedules.models.GameResult;
import com.hockeymanager.application.schedules.repositories.GameResultsRepository;
import com.hockeymanager.application.schedules.repositories.GamesRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
public class TurnsEngineService {
    private final GamesEngineService gamesEngineService;
    private final GamesRepository gamesRepository;
    private final GameResultsRepository gameResultsRepository;
    private final DynastiesRepository dynastiesRepository;

    public void playTurn(String dynastyId) {
        var dynasty = dynastiesRepository.findById(dynastyId)
                .orElseThrow(() -> new IllegalArgumentException("Dynasty not found"));
        var runUpTo = dynasty.getCurrentState().getCurrentDate().plusMonths(1);
        var games = gamesRepository.findAllByDynastyId(dynasty.getId());

        for (var game : games) {
            if (game.getDate().isBefore(runUpTo)
                    && !game.getDate().isBefore(dynasty.getCurrentState().getCurrentDate())) {
                var gameResult = gamesEngineService.simulateGame(game.getHomeTeam(), game.getAwayTeam());

                var result = new GameResult();

                result.setGame(game);
                result.setHomeScore(gameResult.getHomeScore());
                result.setAwayScore(gameResult.getAwayScore());
                result.setDynastyId(dynasty.getId());

                gameResultsRepository.save(result);
            }
        }

        dynasty.getCurrentState().setCurrentDate(runUpTo);
        dynastiesRepository.save(dynasty);
    }
}
