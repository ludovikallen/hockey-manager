package com.hockeymanager.application.schedules.services;

import com.hockeymanager.application.schedules.models.Game;
import com.hockeymanager.application.teams.models.Team;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@NoArgsConstructor
public class SchedulesService {
    private static final int GAMES_PER_TEAM = 82;
    private static final int HOME_GAMES_PER_TEAM = 41;
    private static final int AWAY_GAMES_PER_TEAM = 41;
    private static final int NON_DIVISION_MATCHUP_WEIGHT = 4;
    private static final int DIVISION_MATCHUP_WEIGHT = 7;

    private static int NUMBER_OF_GAMES_VS_OPPONENT = 0;

    public List<Game> generateSchedule(int candidateCount, List<Team> teams, int year) {
        if (year < 1800 || year > 3000) {
            throw new IllegalArgumentException("Year must be between 1800 and 3000");
        } else if (teams.size() < 2) {
            throw new IllegalArgumentException("At least two teams are required to generate a schedule");
        } else if (teams.size() > 100) {
            throw new IllegalArgumentException("A maximum of 50 teams are allowed");
        } else if (candidateCount < 1) {
            throw new IllegalArgumentException("At least one candidate is required");
        } else if (candidateCount > 1000) {
            throw new IllegalArgumentException("A maximum of 1000 candidates are allowed");
        }

        var numberOfGamesAgainstEachOpponent = GAMES_PER_TEAM / (teams.size() - 1);
        List<List<Game>> candidates = new ArrayList<>();
        for (int i = 0; i < candidateCount; i++) {
            List<Game> candidate = generateCandidate(teams, year, numberOfGamesAgainstEachOpponent);
            if (isValidSchedule(candidate, teams)) {
                candidates.add(candidate);
            }
        }

        if (candidates.isEmpty()) {
            return null;
        }

        Map<List<Game>, Double> evaluatedCandidates = new HashMap<>();
        for (List<Game> candidate : candidates) {
            double score = evaluateScheduleQuality(candidate, teams);
            evaluatedCandidates.put(candidate, score);
        }
        var sortedCandidates = evaluatedCandidates.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .toList().reversed();

        return sortedCandidates.get(0).getKey().stream().sorted(
                (game1, game2) -> game1.getDate().compareTo(game2.getDate()))
                .toList();
    }

    private List<Game> generateCandidate(List<Team> teams, int year, int numberOfGamesAgainstEachOpponent) {
        Random random = new Random();

        LocalDate seasonStart = getSeasonStartDate(year);
        LocalDate seasonEnd = getSeasonEndDate(year + 1);

        Map<String, Integer> homeGamesCount = new HashMap<>();
        Map<String, Integer> awayGamesCount = new HashMap<>();
        Map<String, List<LocalDate>> teamGameDates = new HashMap<>();

        for (Team team : teams) {
            homeGamesCount.put(team.getId(), 0);
            awayGamesCount.put(team.getId(), 0);
            teamGameDates.put(team.getId(), new ArrayList<>());
        }

        List<TeamPair> possibleMatchups = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            for (int j = i + 1; j < teams.size(); j++) {
                Team team1 = teams.get(i);
                Team team2 = teams.get(j);

                int weight = team1.getDivision().equals(team2.getDivision()) ? DIVISION_MATCHUP_WEIGHT
                        : NON_DIVISION_MATCHUP_WEIGHT;
                for (int k = 0; k < weight; k++) {
                    possibleMatchups.add(new TeamPair(team1, team2));
                }
            }
        }

        List<LocalDate> availableDates = getAvailableDates(seasonStart, seasonEnd);
        Collections.shuffle(availableDates, random);

        List<Game> schedule = new ArrayList<>();
        final int totalNumberOfDates = availableDates.size();
        while (!availableDates.isEmpty() && !isScheduleComplete(homeGamesCount, awayGamesCount, teams)) {
            LocalDate gameDate = availableDates.remove(0);

            List<TeamPair> availableMatchups = new ArrayList<>(possibleMatchups);
            Collections.shuffle(availableMatchups, random);

            for (TeamPair pair : availableMatchups) {
                Team team1 = pair.team1;
                Team team2 = pair.team2;

                var canScheduleTeam1Home = canScheduleGame(team1, team2, gameDate, homeGamesCount, awayGamesCount,
                        teamGameDates);
                var canScheduleTeam2Home = canScheduleGame(team2, team1, gameDate, homeGamesCount, awayGamesCount,
                        teamGameDates);

                if (!canScheduleTeam1Home && !canScheduleTeam2Home) {
                    continue;
                }

                var matchups = schedule.stream()
                        .filter(game -> (game.getHomeTeam().equals(team1) && game.getAwayTeam().equals(team2))
                                || (game.getHomeTeam().equals(team2) && game.getAwayTeam().equals(team1)))
                        .toList();
                if (matchups.size() >= NUMBER_OF_GAMES_VS_OPPONENT) {
                    // This is used to ease schedule generation
                    // when there are few available dates left
                    var ratioOfScheduledDates = (double) availableDates.size() / totalNumberOfDates;
                    if (random.nextDouble() < ratioOfScheduledDates) {
                        continue;
                    }
                }

                if (canScheduleTeam1Home && canScheduleTeam2Home) {
                    var team1HomeGames = matchups.stream()
                            .filter(game -> game.getHomeTeam().equals(team1))
                            .count();
                    var team2HomeGames = matchups.stream()
                            .filter(game -> game.getHomeTeam().equals(team2))
                            .count();

                    if (team1HomeGames < team2HomeGames) {
                        schedule.add(new Game(team1, team2, gameDate));
                        updateScheduleTracking(team1, team2, gameDate, homeGamesCount, awayGamesCount, teamGameDates);
                    } else if (team2HomeGames < team1HomeGames) {
                        schedule.add(new Game(team2, team1, gameDate));
                        updateScheduleTracking(team2, team1, gameDate, homeGamesCount, awayGamesCount, teamGameDates);
                    } else {
                        if (random.nextBoolean()) {
                            schedule.add(new Game(team1, team2, gameDate));
                            updateScheduleTracking(team1, team2, gameDate, homeGamesCount, awayGamesCount,
                                    teamGameDates);
                        } else {
                            schedule.add(new Game(team2, team1, gameDate));
                            updateScheduleTracking(team2, team1, gameDate, homeGamesCount, awayGamesCount,
                                    teamGameDates);
                        }
                    }
                } else if (canScheduleTeam1Home) {
                    schedule.add(new Game(team1, team2, gameDate));
                    updateScheduleTracking(team1, team2, gameDate, homeGamesCount, awayGamesCount, teamGameDates);
                } else if (canScheduleTeam2Home) {
                    schedule.add(new Game(team2, team1, gameDate));
                    updateScheduleTracking(team2, team1, gameDate, homeGamesCount, awayGamesCount, teamGameDates);
                }
            }
        }

        return schedule;
    }

    private boolean canScheduleGame(Team homeTeam, Team awayTeam, LocalDate date,
            Map<String, Integer> homeGamesCount, Map<String, Integer> awayGamesCount,
            Map<String, List<LocalDate>> teamGameDates) {
        if (homeGamesCount.get(homeTeam.getId()) >= HOME_GAMES_PER_TEAM) {
            return false;
        }
        if (awayGamesCount.get(awayTeam.getId()) >= AWAY_GAMES_PER_TEAM) {
            return false;
        }

        if (teamGameDates.get(homeTeam.getId()).contains(date)) {
            return false;
        }
        if (teamGameDates.get(awayTeam.getId()).contains(date)) {
            return false;
        }

        if (hasThreeConsecutiveGameDays(homeTeam, date, teamGameDates)) {
            return false;
        }
        if (hasThreeConsecutiveGameDays(awayTeam, date, teamGameDates)) {
            return false;
        }

        return true;
    }

    private boolean hasThreeConsecutiveGameDays(Team team, LocalDate date, Map<String, List<LocalDate>> teamGameDates) {
        List<LocalDate> games = teamGameDates.get(team.getId());

        boolean gameTwoDaysAgo = games.contains(date.minusDays(2));
        boolean gameOneDayAgo = games.contains(date.minusDays(1));

        boolean gameTomorow = games.contains(date.plusDays(1));
        boolean gameDayAfterTomorrow = games.contains(date.plusDays(2));

        return (gameTwoDaysAgo && gameOneDayAgo) || (gameOneDayAgo && gameTomorow)
                || (gameTomorow && gameDayAfterTomorrow);
    }

    private void updateScheduleTracking(Team homeTeam, Team awayTeam, LocalDate date,
            Map<String, Integer> homeGamesCount, Map<String, Integer> awayGamesCount,
            Map<String, List<LocalDate>> teamGameDates) {
        homeGamesCount.put(homeTeam.getId(), homeGamesCount.get(homeTeam.getId()) + 1);

        awayGamesCount.put(awayTeam.getId(), awayGamesCount.get(awayTeam.getId()) + 1);

        teamGameDates.get(homeTeam.getId()).add(date);
        teamGameDates.get(awayTeam.getId()).add(date);

        teamGameDates.get(homeTeam.getId()).sort(LocalDate::compareTo);
        teamGameDates.get(awayTeam.getId()).sort(LocalDate::compareTo);
    }

    private boolean isScheduleComplete(Map<String, Integer> homeGamesCount, Map<String, Integer> awayGamesCount,
            List<Team> teams) {
        for (Team team : teams) {
            if (homeGamesCount.get(team.getId()) < HOME_GAMES_PER_TEAM) {
                return false;
            }
            if (awayGamesCount.get(team.getId()) < AWAY_GAMES_PER_TEAM) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidSchedule(List<Game> schedule, List<Team> teams) {
        Map<String, Integer> homeGamesCount = new HashMap<>();
        Map<String, Integer> awayGamesCount = new HashMap<>();
        Map<String, List<LocalDate>> teamGameDates = new HashMap<>();

        for (Team team : teams) {
            homeGamesCount.put(team.getId(), 0);
            awayGamesCount.put(team.getId(), 0);
            teamGameDates.put(team.getId(), new ArrayList<>());
        }

        for (Game game : schedule) {
            Team homeTeam = game.getHomeTeam();
            Team awayTeam = game.getAwayTeam();
            LocalDate date = game.getDate();

            // Update counts
            homeGamesCount.put(homeTeam.getId(), homeGamesCount.getOrDefault(homeTeam.getId(), 0) + 1);
            awayGamesCount.put(awayTeam.getId(), awayGamesCount.getOrDefault(awayTeam.getId(), 0) + 1);

            // Update game dates
            teamGameDates.get(homeTeam.getId()).add(date);
            teamGameDates.get(awayTeam.getId()).add(date);
        }

        for (Team team : teams) {
            // Check total games
            int totalGames = homeGamesCount.getOrDefault(team.getId(), 0)
                    + awayGamesCount.getOrDefault(team.getId(), 0);
            if (totalGames != GAMES_PER_TEAM) {
                return false;
            }

            if (homeGamesCount.getOrDefault(team.getId(), 0) != HOME_GAMES_PER_TEAM) {
                return false;
            }
            if (awayGamesCount.getOrDefault(team.getId(), 0) != AWAY_GAMES_PER_TEAM) {
                return false;
            }

            List<LocalDate> dates = teamGameDates.get(team.getId());
            Collections.sort(dates);

            for (int i = 0; i < dates.size() - 2; i++) {
                if (dates.get(i).plusDays(1).equals(dates.get(i + 1))
                        && dates.get(i + 1).plusDays(1).equals(dates.get(i + 2))) {
                    return false;
                }
            }
        }

        return true;
    }

    private double evaluateScheduleQuality(List<Game> schedule, List<Team> teams) {
        Map<String, List<LocalDate>> teamGameDates = new HashMap<>();

        Map<String, Map<String, Integer>> teamMatchups = new HashMap<>();

        Map<String, Map<String, int[]>> matchupHomeAwayCount = new HashMap<>();

        Map<String, Map<String, Integer>> teamDivisionMatchups = new HashMap<>();

        for (Team team : teams) {
            teamGameDates.put(team.getId(), new ArrayList<>());
            teamMatchups.put(team.getId(), new HashMap<>());
            teamDivisionMatchups.put(team.getId(), new HashMap<>());
            matchupHomeAwayCount.put(team.getId(), new HashMap<>());
        }

        for (Game game : schedule) {
            Team homeTeam = game.getHomeTeam();
            Team awayTeam = game.getAwayTeam();
            LocalDate date = game.getDate();

            teamGameDates.get(homeTeam.getId()).add(date);
            teamGameDates.get(awayTeam.getId()).add(date);

            teamMatchups.get(homeTeam.getId())
                    .put(awayTeam.getId(), teamMatchups.get(homeTeam.getId())
                            .getOrDefault(awayTeam.getId(), 0) + 1);
            teamMatchups.get(awayTeam.getId())
                    .put(homeTeam.getId(), teamMatchups.get(awayTeam.getId())
                            .getOrDefault(homeTeam.getId(), 0) + 1);

            if (!matchupHomeAwayCount.get(homeTeam.getId()).containsKey(awayTeam.getId())) {
                matchupHomeAwayCount.get(homeTeam.getId()).put(awayTeam.getId(), new int[] { 0, 0 });
                matchupHomeAwayCount.get(awayTeam.getId()).put(homeTeam.getId(), new int[] { 0, 0 });
            }

            matchupHomeAwayCount.get(homeTeam.getId()).get(awayTeam.getId())[0]++;
            matchupHomeAwayCount.get(awayTeam.getId()).get(homeTeam.getId())[1]++;

            if (homeTeam.getDivision().equals(awayTeam.getDivision())) {
                teamDivisionMatchups.get(homeTeam.getId())
                        .put(awayTeam.getId(), teamDivisionMatchups.get(homeTeam.getId())
                                .getOrDefault(awayTeam.getId(), 0) + 1);
                teamDivisionMatchups.get(awayTeam.getId())
                        .put(homeTeam.getId(), teamDivisionMatchups.get(awayTeam.getId())
                                .getOrDefault(homeTeam.getId(), 0) + 1);
            }
        }

        for (String teamId : teamGameDates.keySet()) {
            Collections.sort(teamGameDates.get(teamId));
        }

        double score = 0.0;
        for (Team team : teams) {
            List<LocalDate> dates = teamGameDates.get(team.getId());

            // Analyze games per week
            Map<Integer, Integer> gamesPerWeek = new HashMap<>();
            for (LocalDate date : dates) {
                int weekOfYear = date.get(java.time.temporal.WeekFields.ISO.weekOfYear());
                gamesPerWeek.put(weekOfYear, gamesPerWeek.getOrDefault(weekOfYear, 0) + 1);
            }

            double weekScore = gamesPerWeek.values().stream()
                    .mapToDouble(games -> Math.max(0, 4 - Math.abs(games - 3)))
                    .average()
                    .orElse(0);
            score += weekScore * 10;

            double restDaysScore = 0;
            for (int i = 0; i < dates.size() - 1; i++) {
                long daysBetween = dates.get(i).until(dates.get(i + 1), java.time.temporal.ChronoUnit.DAYS);
                if (daysBetween > 5) {
                    restDaysScore -= (daysBetween - 5); // Penalty for long breaks
                }
            }
            score += restDaysScore;

            double divisionMatchupScore = teamDivisionMatchups.get(team.getId()).values().stream()
                    .mapToInt(Integer::intValue)
                    .sum();
            score += divisionMatchupScore * 0.5;

            double matchupBalanceScore = 0;
            Map<String, Integer> opponentMatchups = teamMatchups.get(team.getId());

            for (Map.Entry<String, Integer> matchup : opponentMatchups.entrySet()) {
                String opponentId = matchup.getKey();
                int matchupCount = matchup.getValue();

                Team opponent = teams.stream()
                        .filter(t -> t.getId().equals(opponentId))
                        .findFirst()
                        .orElse(null);

                if (opponent != null) {
                    int idealMatchups = team.getDivision().equals(opponent.getDivision()) ? 4
                            : 2;

                    if (matchupCount > idealMatchups) {
                        matchupBalanceScore -= Math.pow((matchupCount - idealMatchups) + 1, 2) * 2;
                    } else if (matchupCount < idealMatchups) {
                        matchupBalanceScore -= Math.pow((idealMatchups - matchupCount) + 1, 2);
                    }

                    int[] homeAwayStats = matchupHomeAwayCount.get(team.getId()).get(opponentId);
                    int homeGames = homeAwayStats[0];
                    int awayGames = homeAwayStats[1];
                    int totalGames = homeGames + awayGames;

                    double homeRatio = (double) homeGames / totalGames;

                    if (totalGames >= 2) {
                        if (homeRatio < 0.33 || homeRatio > 0.67) {
                            double imbalancePenalty = Math.pow(Math.abs(homeRatio - 0.5) * 10, 2) * totalGames;
                            matchupBalanceScore -= imbalancePenalty;
                        }

                        if (homeGames == 0 || awayGames == 0) {
                            matchupBalanceScore -= totalGames * 5;
                        }
                    }
                }
            }
            score += matchupBalanceScore;
        }

        return score;
    }

    private LocalDate getSeasonStartDate(int year) {
        return LocalDate.of(year, Month.OCTOBER, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.TUESDAY))
                .with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
    }

    private LocalDate getSeasonEndDate(int year) {
        return LocalDate.of(year, Month.APRIL, 1)
                .with(TemporalAdjusters.firstInMonth(DayOfWeek.SUNDAY));
    }

    private List<LocalDate> getAvailableDates(LocalDate start, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate current = start;

        while (!current.isAfter(end)) {
            dates.add(current);
            current = current.plusDays(1);
        }

        return dates;
    }

    private static class TeamPair {
        Team team1;
        Team team2;

        public TeamPair(Team team1, Team team2) {
            this.team1 = team1;
            this.team2 = team2;
        }
    }
}