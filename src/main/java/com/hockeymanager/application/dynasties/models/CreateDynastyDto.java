package com.hockeymanager.application.dynasties.models;

import com.hockeymanager.application.players.models.Goalie;
import com.hockeymanager.application.players.models.Player;
import com.hockeymanager.application.teams.models.Team;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreateDynastyDto {
    @NotBlank
    @Nonnull
    private String name;

    @Nonnull
    private String teamId;

    @Nonnull
    @NotEmpty
    private List<Team> teams;

    @Nonnull
    @NotEmpty
    private List<Player> players;

    @Nonnull
    @NotEmpty
    private List<Goalie> goalies;
}
