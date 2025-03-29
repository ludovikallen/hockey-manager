package com.hockeymanager.application.teams.repositories;

import com.hockeymanager.application.teams.models.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TeamsRepository extends JpaRepository<Team, String>, JpaSpecificationExecutor<Team> {
    List<Team> findAllByDynastyId(String dynastyId);
}
