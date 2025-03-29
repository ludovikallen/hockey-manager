package com.hockeymanager.application.players.repositories;

import com.hockeymanager.application.players.models.Goalie;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GoaliesRepository extends JpaRepository<Goalie, UUID>, JpaSpecificationExecutor<Goalie> {
}
