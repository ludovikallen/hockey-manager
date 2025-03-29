package com.hockeymanager.application.players.services;

import com.hockeymanager.application.players.models.Player;
import com.hockeymanager.application.players.repositories.PlayersRepository;
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
public class PlayersService {
    private final PlayersRepository playersRepository;

    public List<@NonNull Player> findAllByTeamId(String teamId) {
        return playersRepository.findAllByTeamId(teamId);
    }
}
