package com.hockeymanager.application.schedules.models;

import com.hockeymanager.application.teams.models.Team;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Game {
    private Team homeTeam;
    private Team awayTeam;
    private LocalDate date;
}
