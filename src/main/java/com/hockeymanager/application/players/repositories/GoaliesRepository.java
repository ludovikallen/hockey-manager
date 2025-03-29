package com.hockeymanager.application.players.repositories;

import com.hockeymanager.application.players.models.Goalie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoaliesRepository extends JpaRepository<Goalie, String>, JpaSpecificationExecutor<Goalie> {
    List<Goalie> findAllByTeamId(String teamId);

    void deleteAllByDynastyId(String dynastyId);
}
