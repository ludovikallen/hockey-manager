package com.hockeymanager.application.patches.models;

import com.hockeymanager.application.teams.models.Team;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class TeamCsv {
    @CsvBindByName(column = "Name")
    private String name;

    @CsvBindByName(column = "Abbreviation")
    private String abbreviation;

    @CsvBindByName(column = "DetailedDivision")
    private String division;

    public static List<TeamCsv> fromCsv(String path) throws IllegalStateException, IOException {
        try (InputStreamReader reader = new InputStreamReader(new FileInputStream(path), StandardCharsets.ISO_8859_1)) {
            CsvToBean<TeamCsv> csvToBean = new CsvToBeanBuilder<TeamCsv>(reader)
                    .withType(TeamCsv.class)
                    .build();

            return csvToBean.parse();
        }
    }

    public static Team toTeam(TeamCsv teamCsv) {
        Team team = new Team();

        team.setName(teamCsv.name);
        team.setAbbreviation(teamCsv.abbreviation);
        team.setDivision(teamCsv.division);

        return team;
    }
}
