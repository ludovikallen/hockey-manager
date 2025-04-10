package com.hockeymanager.application.engine.services;

import com.hockeymanager.application.engine.models.GameResult;
import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.GlobalGameState;
import com.hockeymanager.application.engine.models.goap.Planner;
import com.hockeymanager.application.engine.models.goap.PlayerGameState;
import com.hockeymanager.application.engine.models.goap.actions.ChasePuckAction;
import com.hockeymanager.application.engine.models.goap.actions.ScoreGoalAction;
import com.hockeymanager.application.engine.models.goap.actions.TakePuckAction;
import com.hockeymanager.application.players.models.Player;
import com.hockeymanager.application.players.models.Position;
import com.hockeymanager.application.players.repositories.PlayersRepository;
import com.hockeymanager.application.teams.models.Team;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
public class GamesEngineService {
    private PlayersRepository playersRepository;

    public GameResult simulateGame(Team homeTeam, Team awayTeam) {
        GlobalGameState.resetGame();

        List<Player> homePlayers = playersRepository.findAllByTeamId(homeTeam.getId()).stream()
                .filter(player -> player.getPosition() != Position.GOALIE) // Enlever les gardiens
                .sorted(Comparator.comparingInt(Player::getCurrentAbility).reversed()) // Classer en ordre décroissant
                .limit(5) // Ressortir les 5 meilleurs joueurs de l'équipe
                .collect(Collectors.toList());
        List<Player> awayPlayers = playersRepository.findAllByTeamId(awayTeam.getId()).stream()
                .filter(player -> player.getPosition() != Position.GOALIE) // Enlever les gardiens
                .sorted(Comparator.comparingInt(Player::getCurrentAbility).reversed()) // Classer en ordre décroissant
                .limit(5) // Ressortir les 5 meilleurs joueurs de l'équipe
                .collect(Collectors.toList());

        List<Action> actions = List.of(new ChasePuckAction(), new TakePuckAction(), new ScoreGoalAction());
        Planner planner = new Planner();

        Map<String, Boolean> goal = Map.of("scored", true);

        while (!GlobalGameState.gameEnded) {
            for (Player player : homePlayers) {
                runPlayerLogic("T1-" + player.getId(), actions, planner, goal);
                if (GlobalGameState.gameEnded) {
                    break;
                }
            }
            for (Player player : awayPlayers) {
                runPlayerLogic("T2-" + player.getId(), actions, planner, goal);
                if (GlobalGameState.gameEnded) {
                    break;
                }
            }
        }

        return new GameResult(GlobalGameState.team1Score, GlobalGameState.team2Score);
    }

    private void runPlayerLogic(String playerId, List<Action> actions, Planner planner, Map<String, Boolean> goal) {
        PlayerGameState state = new PlayerGameState();
        state.put("hasPuck", false);
        state.put("closeToPuck", false);
        state.put("scored", false);

        List<Action> plan = planner.plan(state, goal, actions, playerId);
        if (plan != null) {
            for (Action action : plan) {
                if (!action.perform(playerId)) {
                    break;
                }
                action.getEffects().forEach(state::put);
            }
        }
    }
}
