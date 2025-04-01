package com.hockeymanager.application.unit.schedules.services;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.hockeymanager.application.schedules.services.SchedulesService;
import com.hockeymanager.application.teams.models.Team;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

// Those tests could in theory be flaky since the schedule generation is not deterministic.
// However, the tests are designed to be pratically always successful by using realistic parameters.
@SpringBootTest
public class SchedulesServiceTests {
    @Autowired
    private SchedulesService schedulesService;

    final int numberOfGames = 82;

    @Test
    void generateScheduleWhenValidParametersShouldReturnValidSchedule() {
        // Arrange
        int numberOfTeams = 32;

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 4));
            teams.add(team);
        }

        // Act
        var schedule = schedulesService.generateSchedule(10, teams, 2023);

        // Assert
        assert schedule.size() == (numberOfGames * numberOfTeams) / 2;
    }

    @Test
    void generateScheduleWhenSmallNumberOfTeamsShouldReturnValidSchedule() {
        // Arrange
        int numberOfTeams = 2;

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 4));
            teams.add(team);
        }

        // Act
        var schedule = schedulesService.generateSchedule(10, teams, 2023);

        // Assert
        assert schedule.size() == (numberOfGames * numberOfTeams) / 2;
    }

    @Test
    void generateScheduleWhenOddNumberOfTeamsShouldReturnValidSchedule() {
        // Arrange
        int numberOfTeams = 7;

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 4));
            teams.add(team);
        }

        // Act
        var schedule = schedulesService.generateSchedule(100, teams, 2023);

        // Assert
        assert schedule.size() == (numberOfGames * numberOfTeams) / 2;
    }

    @Test
    void generateScheduleWhenMoreTeamsThanGamesShouldReturnValidSchedule() {
        // Arrange
        int numberOfTeams = 100;

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 8));
            teams.add(team);
        }

        // Act
        var schedule = schedulesService.generateSchedule(10, teams, 2023);

        // Assert
        assert schedule.size() == (numberOfGames * numberOfTeams) / 2;
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 101 })
    void generateScheduleWhenInvalidNumberOfTeamsShouldThrow(int numberOfTeams) {
        // Arrange
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 8));
            teams.add(team);
        }

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> schedulesService.generateSchedule(100, teams, 2023));
    }

    @ParameterizedTest
    @ValueSource(ints = { 0, -1, 1001, Integer.MAX_VALUE, Integer.MIN_VALUE })
    void generateScheduleWhenInvalidNumberOfCandidateShouldThrow(int numberOfCandidates) {
        // Arrange
        int numberOfTeams = 32;

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 4));
            teams.add(team);
        }

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> schedulesService.generateSchedule(numberOfCandidates, teams, 2023));
    }

    @ParameterizedTest
    @ValueSource(ints = { 1799, 3001, Integer.MAX_VALUE, Integer.MIN_VALUE })
    void generateScheduleWhenInvalidYearShouldThrow(int year) {
        // Arrange
        int numberOfTeams = 1;

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 8));
            teams.add(team);
        }

        // Act & Assert
        assertThrows(IllegalArgumentException.class,
                () -> schedulesService.generateSchedule(100, teams, year));
    }

    @Test
    @Disabled("This test is used to test the schedule generation and should not be run in a normal test suite.")
    void generateScheduleWhenValidParametersDisplayValidSchedule() {
        // Arrange
        int numberOfTeams = 32;

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numberOfTeams; i++) {
            var team = new Team();
            team.setName("Team " + i);
            team.setAbbreviation("T" + i);
            team.setDivision("Division " + (i % 4));
            teams.add(team);
        }

        // Act
        var schedule = schedulesService.generateSchedule(100, teams, 2023);

        // Display the schedule
        var team1Schedule = schedule.stream()
                .filter(game -> game.getHomeTeam().getName().equals("Team 1")
                        || game.getAwayTeam().getName().equals("Team 1"))
                .toList();

        for (var game : team1Schedule) {
            System.out.println(
                    game.getDate() + ": " + game.getAwayTeam().getAbbreviation() + " @ "
                            + game.getHomeTeam().getAbbreviation());
        }

        Map<String, Map<String, Pair<Integer, Integer>>> teamStats = new HashMap<>();
        for (var game : schedule) {
            var homeTeam = game.getHomeTeam();
            var awayTeam = game.getAwayTeam();
            if (!teamStats.containsKey(homeTeam.getAbbreviation())) {
                teamStats.put(homeTeam.getAbbreviation(), new HashMap<>());
            }
            if (!teamStats.containsKey(awayTeam.getAbbreviation())) {
                teamStats.put(awayTeam.getAbbreviation(), new HashMap<>());
            }
            teamStats.get(homeTeam.getAbbreviation()).putIfAbsent(awayTeam.getAbbreviation(), Pair.of(0, 0));
            teamStats.get(awayTeam.getAbbreviation()).putIfAbsent(homeTeam.getAbbreviation(), Pair.of(0, 0));

            var homeTeamStats = teamStats.get(homeTeam.getAbbreviation()).get(awayTeam.getAbbreviation());
            homeTeamStats = Pair.of(homeTeamStats.getFirst() + 1, homeTeamStats.getSecond());
            teamStats.get(homeTeam.getAbbreviation()).put(awayTeam.getAbbreviation(), homeTeamStats);

            var awayTeamStats = teamStats.get(awayTeam.getAbbreviation()).get(homeTeam.getAbbreviation());
            awayTeamStats = Pair.of(awayTeamStats.getFirst(), awayTeamStats.getSecond() + 1);
            teamStats.get(awayTeam.getAbbreviation()).put(homeTeam.getAbbreviation(), awayTeamStats);
        }

        for (var entry : teamStats.entrySet()) {
            System.out.println(entry.getKey() + ":");
            for (var key : entry.getValue().keySet()) {
                var value = entry.getValue().get(key);
                System.out.println(
                        "  " + key + ": " + value.getFirst() + " home games, " + value.getSecond() + " away games");
            }
        }
    }
}
