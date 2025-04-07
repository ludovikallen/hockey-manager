package com.hockeymanager.application.engine.services;

import com.hockeymanager.application.dynasties.repositories.DynastiesRepository;
import com.hockeymanager.application.news.models.News;
import com.hockeymanager.application.news.repositories.NewsRepository;
import com.hockeymanager.application.schedules.models.GameResult;
import com.hockeymanager.application.schedules.repositories.GameResultsRepository;
import com.hockeymanager.application.schedules.repositories.GamesRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
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
    private final NewsRepository newsRepository;

    public void playTurn(String dynastyId) {
        var dynasty = dynastiesRepository.findById(dynastyId)
                .orElseThrow(() -> new IllegalArgumentException("Dynasty not found"));
        var currentTurn = dynasty.getCurrentState().getCurrentTurnId() + 1;

        var runUpTo = dynasty.getCurrentState().getCurrentDate().plusMonths(1);
        var games = gamesRepository.findAllByDynastyId(dynasty.getId());

        Pair<Integer, Integer> currentTeamResult = Pair.of(0, 0);

        for (var game : games) {
            if (game.getDate().isBefore(runUpTo)
                    && !game.getDate().isBefore(dynasty.getCurrentState().getCurrentDate())) {
                var gameResult = gamesEngineService.simulateGame(game.getHomeTeam(), game.getAwayTeam());

                var result = new GameResult();

                result.setGame(game);
                result.setHomeScore(gameResult.getHomeScore());
                result.setAwayScore(gameResult.getAwayScore());
                result.setDynastyId(dynasty.getId());

                if (game.getAwayTeam().getId().equals(dynasty.getTeam().getId())) {
                    if (result.getAwayScore() > result.getHomeScore()) {
                        currentTeamResult = Pair.of(currentTeamResult.getFirst() + 1, currentTeamResult.getSecond());
                    } else {
                        currentTeamResult = Pair.of(currentTeamResult.getFirst(), currentTeamResult.getSecond() + 1);
                    }
                } else if (game.getHomeTeam().getId().equals(dynasty.getTeam().getId())) {
                    if (result.getHomeScore() > result.getAwayScore()) {
                        currentTeamResult = Pair.of(currentTeamResult.getFirst() + 1, currentTeamResult.getSecond());
                    } else {
                        currentTeamResult = Pair.of(currentTeamResult.getFirst(), currentTeamResult.getSecond() + 1);
                    }
                }

                gameResultsRepository.save(result);
            }
        }

        dynasty.getCurrentState().setCurrentDate(runUpTo);
        dynasty.getCurrentState().setCurrentTurnId(currentTurn);
        dynastiesRepository.save(dynasty);

        var news = new News();
        news.setTurnId(currentTurn);
        news.setDynastyId(dynastyId);
        news.setTitle("Turn " + (currentTurn - 1) + " recap");
        news.setDescription("Record: " + currentTeamResult.getFirst() + " - " + currentTeamResult.getSecond());
        newsRepository.save(news);
    }
}
