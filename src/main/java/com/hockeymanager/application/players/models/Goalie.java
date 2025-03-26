package com.hockeymanager.application.players.models;

import com.hockeymanager.application.data.BaseEntity;
import com.hockeymanager.application.teams.models.Team;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "goalies")
public class Goalie extends BaseEntity {
    private String dynastyId;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Handedness handedness;

    private LocalDate birthdate;

    private int heightInCms;

    private int weightInKgs;

    private int number;

    private int capHit;

    private LocalDate contractEnd;

    private String nationality;

    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    @Enumerated(EnumType.STRING)
    private GoalieStyle style;

    // Skills attributes

    private int ambition;

    private int determination;

    private int loyalty;

    private int pressure;

    private int professionalism;

    private int sportsmanship;

    private int temperament;

    private int currentAbility;

    private int potentialAbility;

    private int internationalSquadNumber;

    private int aggression;

    private int anticipation;

    private int bravery;

    private int consistency;

    private int decisions;

    private int dirtiness;

    private int flair;

    private int importantMatches;

    private int leadership;

    private int morale;

    private int passTendency;

    private int teamwork;

    private int workRate;

    private int acceleration;

    private int agility;

    private int balance;

    private int fighting;

    private int injuryProneness;

    private int pace;

    private int stamina;

    private int strength;

    private int agitation;

    private int oneOnOnes;

    private int passing;

    private int pokecheck;

    private int positioning;

    private int stickhandling;

    private int versatility;

    private int blocker;

    private int glove;

    private int rebounds;

    private int recovery;

    private int reflexes;
}