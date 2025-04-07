package com.hockeymanager.application.teams.repositories;

import com.hockeymanager.application.teams.models.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TeamsRepository extends JpaRepository<Team, String>, JpaSpecificationExecutor<Team> {
    List<Team> findAllByDynastyId(String dynastyId);

    @Modifying
    @Query(value = "DELETE FROM teams WHERE dynasty_id = ?1", nativeQuery = true)
    void deleteAllByDynastyId(String dynastyId);
}
