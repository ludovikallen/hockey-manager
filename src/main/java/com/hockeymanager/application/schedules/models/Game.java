package com.hockeymanager.application.schedules.models;

import com.hockeymanager.application.data.BaseEntity;
import com.hockeymanager.application.teams.models.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "games")
public class Game extends BaseEntity {
    public Game(Team homeTeam, Team awayTeam, LocalDate date) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.date = date;
    }

    private String dynastyId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "home_team_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team homeTeam;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "away_team_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Team awayTeam;

    private LocalDate date;
}
