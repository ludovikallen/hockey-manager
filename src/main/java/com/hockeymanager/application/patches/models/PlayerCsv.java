package com.hockeymanager.application.patches.models;

import com.hockeymanager.application.players.models.Handedness;
import com.hockeymanager.application.players.models.Player;
import com.hockeymanager.application.players.models.PlayerStyle;
import com.hockeymanager.application.players.models.Position;
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

public class PlayerCsv {
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

    @CsvBindByName(column = "Creativity")
    private int creativity;

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

    @CsvBindByName(column = "Hitting")
    private int hitting;

    @CsvBindByName(column = "InjuryProneness")
    private int injuryProneness;

    @CsvBindByName(column = "NaturalFitness")
    private int naturalFitness;

    @CsvBindByName(column = "Pace")
    private int pace;

    @CsvBindByName(column = "Stamina")
    private int stamina;

    @CsvBindByName(column = "Strength")
    private int strength;

    @CsvBindByName(column = "Agitation")
    private int agitation;

    @CsvBindByName(column = "Checking")
    private int checking;

    @CsvBindByName(column = "Deflections")
    private int deflections;

    @CsvBindByName(column = "Deking")
    private int deking;

    @CsvBindByName(column = "Faceoffs")
    private int faceoffs;

    @CsvBindByName(column = "Movement")
    private int movement;

    @CsvBindByName(column = "OneOnOnes")
    private int oneOnOnes;

    @CsvBindByName(column = "Passing")
    private int passing;

    @CsvBindByName(column = "Pokecheck")
    private int pokecheck;

    @CsvBindByName(column = "Positioning")
    private int positioning;

    @CsvBindByName(column = "Slapshot")
    private int slapshot;

    @CsvBindByName(column = "Stickhandling")
    private int stickhandling;

    @CsvBindByName(column = "Versatility")
    private int versatility;

    @CsvBindByName(column = "Wristshot")
    private int wristshot;

    public static List<PlayerCsv> fromCsv(String path) throws IllegalStateException, IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.ISO_8859_1)) {
            CsvToBean<PlayerCsv> csvToBean = new CsvToBeanBuilder<PlayerCsv>(reader)
                    .withType(PlayerCsv.class)
                    .withSkipLines(1)
                    .build();

            return csvToBean.stream()
                    .filter(x -> !x.role.startsWith("Goalie") && x.classification.equalsIgnoreCase("Player")).toList();
        }
    }

    public static Player toPlayer(PlayerCsv playerCsv, List<Team> teams) {
        Player player = new Player();

        player.setTeam(teams.stream().filter(x -> playerCsv.clubContracted.equals(x.getName())).findFirst().get());

        player.setFirstName(playerCsv.firstName);
        player.setLastName(playerCsv.secondName);
        player.setPosition(getPositionFromPositionStrength(playerCsv));
        player.setHandedness(playerCsv.hand.equalsIgnoreCase("Right") ? Handedness.RIGHT : Handedness.LEFT);
        player.setBirthdate(LocalDate.parse(playerCsv.dateOfBirth, dateTimeFormatter));
        player.setHeightInCms(playerCsv.heightCm);
        player.setWeightInKgs(playerCsv.weightKg);
        player.setNumber(playerCsv.squadNumber);
        player.setCapHit(playerCsv.estimatedWage);
        player.setContractEnd(LocalDate.parse(playerCsv.contractExpiresClub, dateTimeFormatter));
        player.setNationality(playerCsv.nation);
        player.setStyle(PlayerStyle.fromDisplayName(playerCsv.role));

        player.setAmbition(playerCsv.ambition);
        player.setDetermination(playerCsv.determination);
        player.setLoyalty(playerCsv.loyalty);
        player.setPressure(playerCsv.pressure);
        player.setProfessionalism(playerCsv.professionalism);
        player.setSportsmanship(playerCsv.sportsmanship);
        player.setTemperament(playerCsv.temperament);
        player.setCurrentAbility(playerCsv.currentAbility);
        player.setPotentialAbility(playerCsv.potentialAbility);
        player.setAggression(playerCsv.aggression);
        player.setAnticipation(playerCsv.anticipation);
        player.setBravery(playerCsv.bravery);
        player.setConsistency(playerCsv.consistency);
        player.setDecisions(playerCsv.decisions);
        player.setDirtiness(playerCsv.dirtiness);
        player.setFlair(playerCsv.flair);
        player.setImportantMatches(playerCsv.importantMatches);
        player.setLeadership(playerCsv.leadership);
        player.setMorale(playerCsv.morale);
        player.setPassTendency(playerCsv.passTendency);
        player.setTeamwork(playerCsv.teamwork);
        player.setCreativity(playerCsv.creativity);
        player.setWorkRate(playerCsv.workRate);
        player.setAcceleration(playerCsv.acceleration);
        player.setAgility(playerCsv.agility);
        player.setBalance(playerCsv.balance);
        player.setFighting(playerCsv.fighting);
        player.setHitting(playerCsv.hitting);
        player.setInjuryProneness(playerCsv.injuryProneness);
        player.setNaturalFitness(playerCsv.naturalFitness);
        player.setPace(playerCsv.pace);
        player.setStamina(playerCsv.stamina);
        player.setStrength(playerCsv.strength);
        player.setAgitation(playerCsv.agitation);
        player.setChecking(playerCsv.checking);
        player.setDeflections(playerCsv.deflections);
        player.setDeking(playerCsv.deking);
        player.setFaceoffs(playerCsv.faceoffs);
        player.setMovement(playerCsv.movement);
        player.setOneOnOnes(playerCsv.oneOnOnes);
        player.setPassing(playerCsv.passing);
        player.setPokecheck(playerCsv.pokecheck);
        player.setPositioning(playerCsv.positioning);
        player.setSlapshot(playerCsv.slapshot);
        player.setStickhandling(playerCsv.stickhandling);
        player.setVersatility(playerCsv.versatility);
        player.setWristshot(playerCsv.wristshot);

        return player;
    }

    private static Position getPositionFromPositionStrength(PlayerCsv playerCsv) {
        var highestValue = 0;
        Position currentPosition = Position.CENTER;

        if (playerCsv.leftWing > highestValue) {
            highestValue = playerCsv.leftWing;
            currentPosition = Position.LEFT_WING;
        }
        if (playerCsv.center > highestValue) {
            highestValue = playerCsv.center;
            currentPosition = Position.CENTER;
        }
        if (playerCsv.rightWing > highestValue) {
            highestValue = playerCsv.rightWing;
            currentPosition = Position.RIGHT_WING;
        }
        if (playerCsv.leftDefense > highestValue) {
            highestValue = playerCsv.leftDefense;
            currentPosition = Position.DEFENSE;
        }
        if (playerCsv.rightDefense > highestValue) {
            highestValue = playerCsv.rightDefense;
            currentPosition = Position.DEFENSE;
        }

        return currentPosition;
    }
}