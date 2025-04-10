package com.hockeymanager.application.engine.models.goap.actions;

import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.PlayerGameState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChasePuckAction extends Action {
    public ChasePuckAction() {
        preconditions.put("hasPuck", false);
        effects.put("closeToPuck", true);
        cost = 1f;
    }

    @Override
    public boolean checkProceduralPrecondition(PlayerGameState playerGameState,
            String playerId) {
        return true;
    }

    @Override
    public boolean perform(String playerId) {
        return true;
    }
}
