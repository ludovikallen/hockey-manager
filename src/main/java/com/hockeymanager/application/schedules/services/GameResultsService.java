package com.hockeymanager.application.schedules.services;

import com.hockeymanager.application.schedules.models.GameResult;
import com.hockeymanager.application.schedules.repositories.GameResultsRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import java.util.List;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
public class GameResultsService {
    private final GameResultsRepository gameResultsRepository;

    public List<@NonNull GameResult> findAllByTeamId(String teamId) {
        return gameResultsRepository.findAllByTeamId(teamId);
    }
}
