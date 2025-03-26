package com.hockeymanager.application.players.models;

public enum GoalieStyle {
    GOALIE_BUTTERFLY("Goalie: Butterfly"),
    GOALIE_HYBRID_UNORTHODOX("Goalie: Hybrid/Unorthodox"),
    GOALIE_MIXED("Goalie: Mixed");

    private final String displayName;

    GoalieStyle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static GoalieStyle fromDisplayName(String displayName) {
        for (GoalieStyle role : GoalieStyle.values()) {
            if (role.getDisplayName().equalsIgnoreCase(displayName)) {
                return role;
            }
        }
        return null;
    }
}