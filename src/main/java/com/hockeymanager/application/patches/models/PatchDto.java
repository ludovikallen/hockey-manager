package com.hockeymanager.application.patches.models;

import com.hockeymanager.application.players.models.Goalie;
import com.hockeymanager.application.players.models.Player;
import com.hockeymanager.application.teams.models.Team;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

@Getter
@Setter
@AllArgsConstructor
public class PatchDto {
    @Nonnull
    @NotEmpty
    private List<@NonNull Team> teams;

    @Nonnull
    @NotEmpty
    private List<@NonNull Player> players;

    @Nonnull
    @NotEmpty
    private List<@NonNull Goalie> goalies;
}
