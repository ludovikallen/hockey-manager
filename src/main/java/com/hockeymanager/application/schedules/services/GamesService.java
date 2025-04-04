package com.hockeymanager.application.schedules.services;

import com.hockeymanager.application.schedules.models.Game;
import com.hockeymanager.application.schedules.repositories.GamesRepository;
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
public class GamesService {
    private final GamesRepository gamesRepository;

    public List<@NonNull Game> findAllByTeamId(String teamId) {
        return gamesRepository.findAllByTeamId(teamId);
    }
}
