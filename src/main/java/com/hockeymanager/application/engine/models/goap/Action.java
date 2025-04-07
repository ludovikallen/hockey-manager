package com.hockeymanager.application.engine.models.goap;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Action {
    protected Map<String, Boolean> preconditions;
    protected Map<String, Boolean> effects;
    protected double cost;

    public abstract boolean checkProceduralPrecondition(GameState gameState);

    public abstract boolean perform();
}
