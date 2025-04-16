package com.hockeymanager.application.engine.models.goap;

import java.util.HashMap;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerGameState extends HashMap<String, Boolean> {
    public PlayerGameState copy() {
        PlayerGameState newState = new PlayerGameState();
        newState.putAll(this);
        return newState;
    }
}
