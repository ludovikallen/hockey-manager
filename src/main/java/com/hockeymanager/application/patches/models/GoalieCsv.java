package com.hockeymanager.application.patches.models;

import com.hockeymanager.application.players.models.Goalie;
import com.hockeymanager.application.players.models.GoalieStyle;
import com.hockeymanager.application.players.models.Handedness;
import com.hockeymanager.application.teams.models.Team;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GoalieCsv {
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d.M.yyyy");

    @CsvBindByName(column = "FirstName")
    private String firstName;

    @CsvBindByName(column = "SecondName")
    private String secondName;

    @CsvBindByName(column = "DateOfBirth")
    private String dateOfBirth;

    @CsvBindByName(column = "Nation")
    private String nation;

    @CsvBindByName(column = "SecondNation")
    private String secondNation;

    @CsvBindByName(column = "DeclaredNation")
    private String declaredNation;

    @CsvBindByName(column = "BirthTown")
    private String birthTown;

    @CsvBindByName(column = "Classification")
    private String classification;

    @CsvBindByName(column = "JobForClub")
    private String jobForClub;

    @CsvBindByName(column = "ClubContracted")
    private String clubContracted;

    @CsvBindByName(column = "LeagueContracted")
    private String leagueContracted;

    @CsvBindByName(column = "ClubPlaying")
    private String clubPlaying;

    @CsvBindByName(column = "LeaguePlaying")
    private String leaguePlaying;

    @CsvBindByName(column = "DateJoinedClub")
    private String dateJoinedClub;

    @CsvBindByName(column = "ContractExpiresClub")
    private String contractExpiresClub;

    @CsvBindByName(column = "EstimatedWage")
    private int estimatedWage;

    @CsvBindByName(column = "EstimatedWageWeekly")
    private int estimatedWageWeekly;

    @CsvBindByName(column = "EstimatedValue")
    private int estimatedValue;

    @CsvBindByName(column = "JobForNation")
    private String jobForNation;

    @CsvBindByName(column = "NationContracted")
    private String nationContracted;

    @CsvBindByName(column = "DateJoinedNation")
    private String dateJoinedNation;

    @CsvBindByName(column = "ContractExpiresNation")
    private String contractExpiresNation;

    @CsvBindByName(column = "InternationalApps")
    private int internationalApps;

    @CsvBindByName(column = "InternationalGoals")
    private int internationalGoals;

    @CsvBindByName(column = "InternationalAssists")
    private int internationalAssists;

    @CsvBindByName(column = "FirstNHLContract")
    private String firstNhlContract;

    @CsvBindByName(column = "StanleyCupsWon")
    private int stanleyCupsWon;

    @CsvBindByName(column = "Latest career history")
    private int latestCareerHistory;

    @CsvBindByName(column = "NHL Draft Eligible")
    private boolean nhlDraftEligible;

    @CsvBindByName(column = "NHL Drafted Adaptability")
    private boolean nhlDraftAdaptability;

    @CsvBindByName(column = "Ambition")
    private int ambition;

    @CsvBindByName(column = "Determination")
    private int determination;

    @CsvBindByName(column = "Loyalty")
    private int loyalty;

    @CsvBindByName(column = "Pressure")
    private int pressure;

    @CsvBindByName(column = "Professionalism")
    private int professionalism;

    @CsvBindByName(column = "Sportsmanship")
    private int sportsmanship;

    @CsvBindByName(column = "Temperament")
    private int temperament;

    @CsvBindByName(column = "CurrentAbility")
    private int currentAbility;

    @CsvBindByName(column = "PotentialAbility")
    private int potentialAbility;

    @CsvBindByName(column = "HomeReputation")
    private int homeReputation;

    @CsvBindByName(column = "CurrentReputation")
    private int currentReputation;

    @CsvBindByName(column = "WorldReputation")
    private int worldReputation;

    @CsvBindByName(column = "Goaltender")
    private int goaltender;

    @CsvBindByName(column = "LeftDefense")
    private int leftDefense;

    @CsvBindByName(column = "RightDefense")
    private int rightDefense;

    @CsvBindByName(column = "LeftWing")
    private int leftWing;

    @CsvBindByName(column = "Center")
    private int center;

    @CsvBindByName(column = "RightWing")
    private int rightWing;

    @CsvBindByName(column = "DefensiveRole")
    private int defensiveRole;

    @CsvBindByName(column = "OffensiveRole")
    private int offensiveRole;

    @CsvBindByName(column = "Role")
    private String role;

    @CsvBindByName(column = "Hand")
    private String hand;

    @CsvBindByName(column = "FavouriteNumber")
    private int favouriteNumber;

    @CsvBindByName(column = "SquadNumber")
    private int squadNumber;

    @CsvBindByName(column = "InternationalSquadNumber")
    private int internationalSquadNumber;

    @CsvBindByName(column = "HeightCm")
    private int heightCm;

    @CsvBindByName(column = "WeightKg")
    private int weightKg;

    @CsvBindByName(column = "JnrPreference")
    private String jnrPreference;

    @CsvBindByName(column = "Aggression")
    private int aggression;

    @CsvBindByName(column = "Anticipation")
    private int anticipation;

    @CsvBindByName(column = "Bravery")
    private int bravery;

    @CsvBindByName(column = "Consistency")
    private int consistency;

    @CsvBindByName(column = "Decisions")
    private int decisions;

    @CsvBindByName(column = "Dirtiness")
    private int dirtiness;

    @CsvBindByName(column = "Flair")
    private int flair;

    @CsvBindByName(column = "ImportantMatches")
    private int importantMatches;

    @CsvBindByName(column = "Leadership")
    private int leadership;

    @CsvBindByName(column = "Morale")
    private int morale;

    @CsvBindByName(column = "PassTendency")
    private int passTendency;

    @CsvBindByName(column = "Teamwork")
    private int teamwork;

    @CsvBindByName(column = "WorkRate")
    private int workRate;

    @CsvBindByName(column = "Acceleration")
    private int acceleration;

    @CsvBindByName(column = "Agility")
    private int agility;

    @CsvBindByName(column = "Balance")
    private int balance;

    @CsvBindByName(column = "Fighting")
    private int fighting;

    @CsvBindByName(column = "InjuryProneness")
    private int injuryProneness;

    @CsvBindByName(column = "Pace")
    private int pace;

    @CsvBindByName(column = "Stamina")
    private int stamina;

    @CsvBindByName(column = "Strength")
    private int strength;

    @CsvBindByName(column = "Agitation")
    private int agitation;

    @CsvBindByName(column = "OneOnOnes")
    private int oneOnOnes;

    @CsvBindByName(column = "Passing")
    private int passing;

    @CsvBindByName(column = "Pokecheck")
    private int pokecheck;

    @CsvBindByName(column = "Positioning")
    private int positioning;

    @CsvBindByName(column = "Stickhandling")
    private int stickhandling;

    @CsvBindByName(column = "Versatility")
    private int versatility;

    @CsvBindByName(column = "Blocker")
    private int blocker;

    @CsvBindByName(column = "Glove")
    private int glove;

    @CsvBindByName(column = "Rebounds")
    private int rebounds;

    @CsvBindByName(column = "Recovery")
    private int recovery;

    @CsvBindByName(column = "Reflexes")
    private int reflexes;

    public static List<GoalieCsv> fromCsv(String path) throws IllegalStateException, IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.ISO_8859_1)) {
            CsvToBean<GoalieCsv> csvToBean = new CsvToBeanBuilder<GoalieCsv>(reader)
                    .withType(GoalieCsv.class)
                    .withSkipLines(1)
                    .build();

            return csvToBean.stream()
                    .filter(x -> x.role.startsWith("Goalie") && x.classification.equalsIgnoreCase("Player")).toList();
        }
    }

    public static Goalie toGoalie(GoalieCsv goalieCsv, List<Team> teams) {
        Goalie goalie = new Goalie();

        goalie.setTeam(teams.stream().filter(x -> goalieCsv.clubContracted.equals(x.getName())).findFirst().get());

        goalie.setFirstName(goalieCsv.firstName);
        goalie.setLastName(goalieCsv.secondName);
        goalie.setHandedness(goalieCsv.hand.equalsIgnoreCase("Right") ? Handedness.RIGHT : Handedness.LEFT);
        goalie.setBirthdate(LocalDate.parse(goalieCsv.dateOfBirth, dateTimeFormatter));
        goalie.setHeightInCms(goalieCsv.heightCm);
        goalie.setWeightInKgs(goalieCsv.weightKg);
        goalie.setNumber(goalieCsv.squadNumber);
        goalie.setCapHit(goalieCsv.estimatedWage);
        goalie.setContractEnd(LocalDate.parse(goalieCsv.contractExpiresClub, dateTimeFormatter));
        goalie.setNationality(goalieCsv.nation);
        goalie.setStyle(GoalieStyle.fromDisplayName(goalieCsv.role));

        goalie.setAmbition(goalieCsv.ambition);
        goalie.setDetermination(goalieCsv.determination);
        goalie.setLoyalty(goalieCsv.loyalty);
        goalie.setPressure(goalieCsv.pressure);
        goalie.setProfessionalism(goalieCsv.professionalism);
        goalie.setSportsmanship(goalieCsv.sportsmanship);
        goalie.setTemperament(goalieCsv.temperament);
        goalie.setCurrentAbility(goalieCsv.currentAbility);
        goalie.setPotentialAbility(goalieCsv.potentialAbility);
        goalie.setAggression(goalieCsv.aggression);
        goalie.setAnticipation(goalieCsv.anticipation);
        goalie.setBravery(goalieCsv.bravery);
        goalie.setConsistency(goalieCsv.consistency);
        goalie.setDecisions(goalieCsv.decisions);
        goalie.setDirtiness(goalieCsv.dirtiness);
        goalie.setFlair(goalieCsv.flair);
        goalie.setImportantMatches(goalieCsv.importantMatches);
        goalie.setLeadership(goalieCsv.leadership);
        goalie.setMorale(goalieCsv.morale);
        goalie.setPassTendency(goalieCsv.passTendency);
        goalie.setTeamwork(goalieCsv.teamwork);
        goalie.setWorkRate(goalieCsv.workRate);
        goalie.setAcceleration(goalieCsv.acceleration);
        goalie.setAgility(goalieCsv.agility);
        goalie.setBalance(goalieCsv.balance);
        goalie.setFighting(goalieCsv.fighting);
        goalie.setInjuryProneness(goalieCsv.injuryProneness);
        goalie.setPace(goalieCsv.pace);
        goalie.setStamina(goalieCsv.stamina);
        goalie.setStrength(goalieCsv.strength);
        goalie.setAgitation(goalieCsv.agitation);
        goalie.setOneOnOnes(goalieCsv.oneOnOnes);
        goalie.setPassing(goalieCsv.passing);
        goalie.setPokecheck(goalieCsv.pokecheck);
        goalie.setPositioning(goalieCsv.positioning);
        goalie.setStickhandling(goalieCsv.stickhandling);
        goalie.setVersatility(goalieCsv.versatility);

        goalie.setBlocker(goalieCsv.blocker);
        goalie.setGlove(goalieCsv.glove);
        goalie.setRebounds(goalieCsv.rebounds);
        goalie.setRecovery(goalieCsv.recovery);
        goalie.setReflexes(goalieCsv.reflexes);

        return goalie;
    }
}