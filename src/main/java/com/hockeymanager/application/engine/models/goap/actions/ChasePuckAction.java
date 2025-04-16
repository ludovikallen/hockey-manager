package com.hockeymanager.application.engine.models.goap.actions;

import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.Agent;
import com.hockeymanager.application.engine.models.goap.GlobalGameState;
import com.hockeymanager.application.engine.models.goap.PlayerGameState;
import java.util.List;
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
    public boolean checkProceduralPrecondition(PlayerGameState pgs, GlobalGameState ggs, String playerId) {
        return true;
    }

    @Override
    public boolean perform(Agent agent, GlobalGameState ggs, List<Agent> allAgents) {
        return true;
    }
}