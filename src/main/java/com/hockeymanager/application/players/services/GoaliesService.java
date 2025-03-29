package com.hockeymanager.application.players.services;

import com.hockeymanager.application.players.models.Goalie;
import com.hockeymanager.application.players.repositories.GoaliesRepository;
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
public class GoaliesService {
    private final GoaliesRepository goaliesRepository;

    public List<@NonNull Goalie> findAllByTeamId(String teamId) {
        return goaliesRepository.findAllByTeamId(teamId);
    }
}
