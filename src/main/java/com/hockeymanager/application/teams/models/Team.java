package com.hockeymanager.application.teams.models;

import com.hockeymanager.application.data.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    private String dynastyId;

    private String name;

    private String abbreviation;

    private String division;
}