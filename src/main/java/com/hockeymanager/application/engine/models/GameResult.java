package com.hockeymanager.application.engine.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GameResult {
    private int homeScore;
    private int awayScore;
}
