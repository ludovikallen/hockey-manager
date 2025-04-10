package com.hockeymanager.application.engine.models.goap.actions;

import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.GlobalGameState;
import com.hockeymanager.application.engine.models.goap.PlayerGameState;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TakePuckAction extends Action {
    public TakePuckAction() {
        preconditions.put("closeToPuck", true);
        preconditions.put("hasPuck", false);
        effects.put("hasPuck", true);
        effects.put("closeToPuck", false);
        cost = 1.5f;
    }

    @Override
    public boolean checkProceduralPrecondition(PlayerGameState playerGameState, String playerId) {
        return !GlobalGameState.puckTaken;
    }

    @Override
    public boolean perform(String playerId) {
        GlobalGameState.puckTaken = true;
        return true;
    }
}
