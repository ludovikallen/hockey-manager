package com.hockeymanager.application.players.repositories;

import com.hockeymanager.application.players.models.Player;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PlayersRepository extends JpaRepository<Player, String>, JpaSpecificationExecutor<Player> {
    List<Player> findAllByTeamId(String teamId);

    void deleteAllByDynastyId(String dynastyId);
}
