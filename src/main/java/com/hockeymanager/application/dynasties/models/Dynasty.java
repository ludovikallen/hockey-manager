package com.hockeymanager.application.dynasties.models;

import com.hockeymanager.application.data.BaseEntity;
import com.hockeymanager.application.teams.models.Team;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "dynasties")
public class Dynasty extends BaseEntity {
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "team_id")
    private Team team;

    @Embedded
    private DynastyState currentState;
}
