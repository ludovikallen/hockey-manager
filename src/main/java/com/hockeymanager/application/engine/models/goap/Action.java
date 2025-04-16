package com.hockeymanager.application.engine.models.goap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Action {
    protected Map<String, Boolean> preconditions = new HashMap<>();
    protected Map<String, Boolean> effects = new HashMap<>();
    protected float cost = 1f;

    public abstract boolean checkProceduralPrecondition(PlayerGameState playerGameState,
            GlobalGameState globalGameState, String playerId);

    public abstract boolean perform(Agent agent, GlobalGameState globalGameState, List<Agent> allAgents);

    public boolean isApplicable(PlayerGameState playerGameState) {
        return preconditions.entrySet().stream()
                .allMatch(e -> playerGameState.getOrDefault(e.getKey(), false).equals(e.getValue()));
    }

    public Map<String, Boolean> getEffects() {
        return effects;
    }

    public float getCost() {
        return cost;
    }
}