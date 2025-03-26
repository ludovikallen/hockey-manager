package com.hockeymanager.application.teams.models;

import com.hockeymanager.application.data.BaseEntity;
import com.hockeymanager.application.players.models.Goalie;
import com.hockeymanager.application.players.models.Player;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    private String name;

    private String abbreviation;

    private String division;

    @OneToMany(mappedBy = "team")
    private List<Player> players;

    @OneToMany(mappedBy = "team")
    private List<Goalie> goalies;
}