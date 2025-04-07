package com.hockeymanager.application.engine.models.goap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Agent {
    private int agentId;
    private String agentRole;
    private String teamId;
}
