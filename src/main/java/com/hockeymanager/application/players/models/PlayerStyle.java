package com.hockeymanager.application.players.models;

public enum PlayerStyle {
    // Centre Roles
    CENTRE_ALL_AROUND("Centre: All around"),
    CENTRE_DEFENSIVE("Centre: Defensive"),
    CENTRE_DEFENSIVE_FINESSE("Centre: Defensive (finesse)"),
    CENTRE_DEFENSIVE_PHYSICAL("Centre: Defensive (physical)"),
    CENTRE_ENFORCER("Centre: Enforcer"),
    CENTRE_FINESSE("Centre: Finesse"),
    CENTRE_GRINDER("Centre: Grinder"),
    CENTRE_PLAYMAKER("Centre: Playmaker"),
    CENTRE_PLAYMAKER_FINESSE("Centre: Playmaker (finesse)"),
    CENTRE_PLAYMAKER_PHYSICAL("Centre: Playmaker (physical)"),
    CENTRE_POWER_FORWARD("Centre: Power forward"),
    CENTRE_SNIPER("Centre: Sniper"),
    CENTRE_SNIPER_FINESSE("Centre: Sniper (finesse)"),
    CENTRE_SNIPER_PHYSICAL("Centre: Sniper (physical)"),

    // Defenseman Roles
    DEFENSEMAN_DEFENSIVE("Defence: Defensive"),
    DEFENSEMAN_DEFENSIVE_FINESSE("Defence: Defensive (finesse)"),
    DEFENSEMAN_DEFENSIVE_PHYSICAL("Defence: Defensive (physical)"),
    DEFENSEMAN_ENFORCER("Defence: Enforcer"),
    DEFENSEMAN_OFFENSIVE("Defence: Offensive"),
    DEFENSEMAN_OFFENSIVE_FINESSE("Defence: Offensive (finesse)"),
    DEFENSEMAN_OFFENSIVE_PHYSICAL("Defence: Offensive (physical)"),
    DEFENSEMAN_PLAYMAKER("Defence: Playmaker"),
    DEFENSEMAN_PLAYMAKER_FINESSE("Defence: Playmaker (finesse)"),
    DEFENSEMAN_PLAYMAKER_PHYSICAL("Defence: Playmaker (physical)"),
    DEFENSEMAN_POINTMAN("Defence: Pointman"),
    DEFENSEMAN_POINTMAN_FINESSE("Defence: Pointman (finesse)"),
    DEFENSEMAN_POINTMAN_PHYSICAL("Defence: Pointman (physical)"),
    DEFENSEMAN_RUGGED("Defence: Rugged"),
    DEFENSEMAN_STANDARD("Defence: Standard"),

    // Winger Roles
    WINGER_ALL_AROUND("Winger: All around"),
    WINGER_DEFENSIVE("Winger: Defensive"),
    WINGER_DEFENSIVE_FINESSE("Winger: Defensive (finesse)"),
    WINGER_DEFENSIVE_PHYSICAL("Winger: Defensive (physical)"),
    WINGER_ENFORCER("Winger: Enforcer"),
    WINGER_FINESSE("Winger: Finesse"),
    WINGER_GRINDER("Winger: Grinder"),
    WINGER_PLAYMAKER("Winger: Playmaker"),
    WINGER_PLAYMAKER_FINESSE("Winger: Playmaker (finesse)"),
    WINGER_PLAYMAKER_PHYSICAL("Winger: Playmaker (physical)"),
    WINGER_POWER_FORWARD("Winger: Power forward"),
    WINGER_SNIPER("Winger: Sniper"),
    WINGER_SNIPER_FINESSE("Winger: Sniper (finesse)"),
    WINGER_SNIPER_PHYSICAL("Winger: Sniper (physical)");

    private final String displayName;

    PlayerStyle(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static PlayerStyle fromDisplayName(String displayName) {
        for (PlayerStyle role : PlayerStyle.values()) {
            if (role.getDisplayName().equalsIgnoreCase(displayName)) {
                return role;
            }
        }
        return null;
    }
}