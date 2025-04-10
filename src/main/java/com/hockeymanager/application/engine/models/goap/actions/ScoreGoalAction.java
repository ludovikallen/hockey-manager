package com.hockeymanager.application.engine.models.goap.actions;

import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.GlobalGameState;
import com.hockeymanager.application.engine.models.goap.PlayerGameState;
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
    public boolean checkProceduralPrecondition(PlayerGameState playerGameState, String playerId) {
        return true;
    }

    @Override
    public boolean perform(String playerId) {
        int team = playerId.contains("T1") ? 1 : 2;
        GlobalGameState.scoreGoal(team);
        return true;
    }
}
