package com.hockeymanager.application.schedules.repositories;

import com.hockeymanager.application.schedules.models.GameResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface GameResultsRepository extends JpaRepository<GameResult, String>, JpaSpecificationExecutor<GameResult> {
    @Query(value = """
            SELECT gr.* FROM game_results as gr
            INNER JOIN games as g ON g.id = gr.game_id
            WHERE g.home_team_id = ?1 OR g.away_team_id = ?1
            """, nativeQuery = true)
    List<GameResult> findAllByTeamId(String teamId);

    @Query(value = """
            SELECT gr.* FROM game_results as gr
            INNER JOIN games as g ON g.id = gr.game_id
            WHERE g.dynasty_id = ?1
            """, nativeQuery = true)
    List<GameResult> findAllByDynastyId(String dynastyId);
}
