package com.hockeymanager.application.engine.models.goap.actions;

import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.Agent;
import com.hockeymanager.application.engine.models.goap.GlobalGameState;
import com.hockeymanager.application.engine.models.goap.PlayerGameState;
import java.util.List;
import java.util.Objects;
import java.util.Random;
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
    public boolean checkProceduralPrecondition(PlayerGameState pgs, GlobalGameState ggs, String playerId) {
        return !ggs.puckTaken;
    }

    @Override
    public boolean perform(Agent agent, GlobalGameState ggs, List<Agent> allAgents) {
        if (ggs.puckTaken) {
            // If the puck is already taken, allow only 1/10 chance to take it back
            if (!Objects.equals(ggs.puckHolderId, agent.playerId)) {
                if (new Random().nextInt(10) != 0) {
                    return false; // 1/10 chance
                }
            }
        }

        ggs.puckTaken = true;
        ggs.puckHolderId = agent.playerId;
        for (Agent a : allAgents) {
            a.state.put("hasPuck", a == agent);
        }
        return true;
    }
}