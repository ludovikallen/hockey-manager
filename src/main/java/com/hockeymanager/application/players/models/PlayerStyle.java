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
    DEFENSEMAN_DEFENSIVE("Defenceman: Defensive"),
    DEFENSEMAN_DEFENSIVE_FINESSE("Defenceman: Defensive (finesse)"),
    DEFENSEMAN_DEFENSIVE_PHYSICAL("Defenceman: Defensive (physical)"),
    DEFENSEMAN_ENFORCER("Defenceman: Enforcer"),
    DEFENSEMAN_OFFENSIVE("Defenceman: Offensive"),
    DEFENSEMAN_OFFENSIVE_FINESSE("Defenceman: Offensive (finesse)"),
    DEFENSEMAN_OFFENSIVE_PHYSICAL("Defenceman: Offensive (physical)"),
    DEFENSEMAN_PLAYMAKER("Defenceman: Playmaker"),
    DEFENSEMAN_PLAYMAKER_FINESSE("Defenceman: Playmaker (finesse)"),
    DEFENSEMAN_PLAYMAKER_PHYSICAL("Defenceman: Playmaker (physical)"),
    DEFENSEMAN_POINTMAN("Defenceman: Pointman"),
    DEFENSEMAN_POINTMAN_FINESSE("Defenceman: Pointman (finesse)"),
    DEFENSEMAN_POINTMAN_PHYSICAL("Defenceman: Pointman (physical)"),
    DEFENSEMAN_RUGGED("Defenceman: Rugged"),
    DEFENSEMAN_STANDARD("Defenceman: Standard"),

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