package com.hockeymanager.application.players.repositories;

import com.hockeymanager.application.players.models.Player;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PlayersRepository extends JpaRepository<Player, String>, JpaSpecificationExecutor<Player> {
    List<Player> findAllByTeamId(String teamId);

    @Modifying
    @Query(value = "DELETE FROM players WHERE dynasty_id = ?1", nativeQuery = true)
    void deleteAllByDynastyId(String dynastyId);
}
