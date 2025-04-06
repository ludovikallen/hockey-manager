package com.hockeymanager.application.dynasties.services;

import com.hockeymanager.application.dynasties.models.CreateDynastyDto;
import com.hockeymanager.application.dynasties.models.Dynasty;
import com.hockeymanager.application.dynasties.models.DynastyState;
import com.hockeymanager.application.dynasties.repositories.DynastiesRepository;
import com.hockeymanager.application.players.repositories.GoaliesRepository;
import com.hockeymanager.application.players.repositories.PlayersRepository;
import com.hockeymanager.application.schedules.repositories.GamesRepository;
import com.hockeymanager.application.schedules.services.SchedulesService;
import com.hockeymanager.application.teams.repositories.TeamsRepository;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
@Log4j2
public class DynastiesService {
    private final DynastiesRepository dynastiesRepository;
    private final PlayersRepository playersRepository;
    private final GoaliesRepository goaliesRepository;
    private final TeamsRepository teamsRepository;
    private final SchedulesService schedulesService;
    private final GamesRepository gamesRepository;

    public List<@NonNull Dynasty> findAll() {
        return dynastiesRepository.findAll();
    }

    @Transactional
    public String create(@Valid CreateDynastyDto dynastyToCreate) {
        Dynasty dynasty = new Dynasty();

        dynasty.setName(dynastyToCreate.getName());
        var dynastyState = new DynastyState();
        dynastyState.setCurrentDate(LocalDate.of(2023, 10, 1));
        dynasty.setCurrentState(dynastyState);

        dynastyToCreate.getTeams().forEach(team -> {
            team.setDynastyId(dynasty.getId());
        });
        teamsRepository.saveAll(dynastyToCreate.getTeams());

        var teams = teamsRepository.findAllByDynastyId(dynasty.getId());

        var chosenTeam = dynastyToCreate.getTeams().stream().filter(x -> x.getId().equals(dynastyToCreate.getTeamId()))
                .findFirst().orElseThrow(ValidationException::new);

        dynasty.setTeam(chosenTeam);

        dynastiesRepository.save(dynasty);

        var games = schedulesService.generateSchedule(10, teams, 2023);
        games.forEach(game -> {
            game.setDynastyId(dynasty.getId());
        });
        gamesRepository.saveAll(games);

        dynastyToCreate.getPlayers().forEach(player -> {
            player.setDynastyId(dynasty.getId());
            player.setTeam(teams.stream().filter(x -> x.getId().equals(player.getTeam().getId()))
                    .findFirst().orElseThrow(ValidationException::new));
        });
        playersRepository.saveAll(dynastyToCreate.getPlayers());

        dynastyToCreate.getGoalies().forEach(goalie -> {
            goalie.setDynastyId(dynasty.getId());
            goalie.setTeam(teams.stream().filter(x -> x.getId().equals(goalie.getTeam().getId()))
                    .findFirst().orElseThrow(ValidationException::new));
        });
        goaliesRepository.saveAll(dynastyToCreate.getGoalies());

        return dynasty.getId();
    }

    public Dynasty findById(String id) {
        return dynastiesRepository.findById(id).orElseThrow(ValidationException::new);
    }

    @Transactional
    public void deleteById(String id) {
        dynastiesRepository.deleteById(id);
        playersRepository.deleteAllByDynastyId(id);
        goaliesRepository.deleteAllByDynastyId(id);
        teamsRepository.deleteAllByDynastyId(id);
    }
}
