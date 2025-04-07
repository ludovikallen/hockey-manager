package com.hockeymanager.application.schedules.models;

import com.hockeymanager.application.data.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "game_results")
public class GameResult extends BaseEntity {
    private String dynastyId;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Game game;

    private int homeScore;
    private int awayScore;
}
