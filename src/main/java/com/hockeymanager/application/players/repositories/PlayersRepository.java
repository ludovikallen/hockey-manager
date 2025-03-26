package com.hockeymanager.application.players.repositories;

import com.hockeymanager.application.players.models.Player;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayersRepository extends JpaRepository<Player, UUID>, JpaSpecificationExecutor<Player> {
}
