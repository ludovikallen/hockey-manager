package com.hockeymanager.application.engine.models.goap;

public class Agent {
    public String playerId;
    public PlayerGameState state = new PlayerGameState();
    public int goals = 0;

    public Agent(String id) {
        this.playerId = id;
        state.put("hasPuck", false);
        state.put("closeToPuck", false);
        state.put("scored", false);
    }
}