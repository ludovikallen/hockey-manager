package com.hockeymanager.application.schedules.models;

import com.hockeymanager.application.data.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "game_results")
public class GameResult extends BaseEntity {
    private String dynastyId;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    private int homeScore;
    private int awayScore;
}
