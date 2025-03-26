package com.hockeymanager.application.patches.services;

import com.hockeymanager.application.patches.models.GoalieCsv;
import com.hockeymanager.application.patches.models.ImportPatchDto;
import com.hockeymanager.application.patches.models.PatchDto;
import com.hockeymanager.application.patches.models.PlayerCsv;
import com.hockeymanager.application.patches.models.TeamCsv;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.io.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
public class PatchesService {
    public PatchDto importPatch(@Valid ImportPatchDto patch) {
        try {
            var teamPath = patch.getFolderPath() + "/clubs.csv";
            if (!new File(teamPath).exists()) {
                throw new ValidationException("Teams file not found!");
            }

            var csvTeams = TeamCsv.fromCsv(teamPath);
            var teams = csvTeams.stream().map(x -> TeamCsv.toTeam(x)).toList();

            var playerPath = patch.getFolderPath() + "/players_&_non-players.csv";
            if (!new File(playerPath).exists()) {
                throw new ValidationException("Players file not found!");
            }
            var csvPlayers = PlayerCsv.fromCsv(playerPath);
            var players = csvPlayers.stream().map(x -> PlayerCsv.toPlayer(x, teams)).toList();

            var csvGoalies = GoalieCsv.fromCsv(playerPath);
            var goalies = csvGoalies.stream().map(x -> GoalieCsv.toGoalie(x, teams)).toList();

            return new PatchDto(teams, players, goalies);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            throw new ValidationException("Failed to upload file!", e);
        }
    }
}
