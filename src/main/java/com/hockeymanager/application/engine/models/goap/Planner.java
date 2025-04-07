package com.hockeymanager.application.engine.models.goap;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Planner {
    private List<Action> plannerActions;
}
