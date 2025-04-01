package com.hockeymanager.application.schedules.repositories;

import com.hockeymanager.application.schedules.models.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GamesRepository extends JpaRepository<Game, String>, JpaSpecificationExecutor<Game> {
    @Query(value = "SELECT * FROM games WHERE home_team_id = ?1 OR away_team_id = ?1", nativeQuery = true)
    List<Game> findAllByTeamId(String teamId);
}
