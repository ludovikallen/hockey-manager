package com.hockeymanager.application.engine.models.goap;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameState {
    private Map<String, Boolean> gameState;
}
