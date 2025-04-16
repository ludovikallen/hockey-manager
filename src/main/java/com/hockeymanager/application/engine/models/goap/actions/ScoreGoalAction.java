package com.hockeymanager.application.engine.models.goap.actions;

import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.Agent;
import com.hockeymanager.application.engine.models.goap.GlobalGameState;
import com.hockeymanager.application.engine.models.goap.PlayerGameState;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreGoalAction extends Action {
    public ScoreGoalAction() {
        preconditions.put("hasPuck", true);
        effects.put("scored", true);
        effects.put("hasPuck", false);
        cost = 2f;
    }

    @Override
    public boolean checkProceduralPrecondition(PlayerGameState pgs, GlobalGameState ggs, String playerId) {
        return true;
    }

    @Override
    public boolean perform(Agent agent, GlobalGameState ggs, List<Agent> allAgents) {
        if (!agent.state.getOrDefault("hasPuck", false)) {
            return false;
        }

        int team = agent.playerId.contains("T1") ? 1 : 2;
        if (team == 1) {
            ggs.team1Shots++;
        } else {
            ggs.team2Shots++;
        }

        int roll = new Random().nextInt(4);
        if (roll == 0) {
            ggs.scoreGoal(team);
            agent.goals++;
        } else {
            ggs.puckTaken = true;
            List<Agent> candidates = new ArrayList<>();
            for (Agent a : allAgents) {
                if (roll == 1 && a.playerId.contains("T" + team)) {
                    candidates.add(a);
                } else if (roll >= 2 && !a.playerId.contains("T" + team)) {
                    candidates.add(a);
                }
            }
            if (!candidates.isEmpty()) {
                Agent newOwner = candidates.get(new Random().nextInt(candidates.size()));
                ggs.puckHolderId = newOwner.playerId;
                for (Agent a : allAgents) {
                    a.state.put("hasPuck", a == newOwner);
                }
            }
        }
        return true;
    }
}