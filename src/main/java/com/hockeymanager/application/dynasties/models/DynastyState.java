package com.hockeymanager.application.dynasties.models;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class DynastyState {
    private LocalDate currentDate;

    private int currentTurnId = 1;
}