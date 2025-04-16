package com.hockeymanager.application.engine.services;

import com.hockeymanager.application.engine.models.GameResult;
import com.hockeymanager.application.engine.models.goap.Action;
import com.hockeymanager.application.engine.models.goap.Agent;
import com.hockeymanager.application.engine.models.goap.GlobalGameState;
import com.hockeymanager.application.engine.models.goap.Planner;
import com.hockeymanager.application.engine.models.goap.actions.ChasePuckAction;
import com.hockeymanager.application.engine.models.goap.actions.ScoreGoalAction;
import com.hockeymanager.application.engine.models.goap.actions.TakePuckAction;
import com.hockeymanager.application.players.models.Player;
import com.hockeymanager.application.players.models.Position;
import com.hockeymanager.application.players.repositories.PlayersRepository;
import com.hockeymanager.application.teams.models.Team;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.BrowserCallable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@BrowserCallable
@AnonymousAllowed
@Service
@AllArgsConstructor
public class GamesEngineService {
    private PlayersRepository playersRepository;
    private static final Random random = new Random();

    public GameResult simulateGame(Team homeTeam, Team awayTeam) {
        GlobalGameState ggs = new GlobalGameState();
        ggs.resetPuck(2);

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

        List<Agent> agents = new ArrayList<>();
        for (Player player : homePlayers) {
            agents.add(new Agent("T1-" + player.getId()));
        }

        for (Player player : awayPlayers) {
            agents.add(new Agent("T2-" + player.getId()));
        }

        List<Action> actions = List.of(new ChasePuckAction(), new TakePuckAction(), new ScoreGoalAction());
        Planner planner = new Planner();

        while (!ggs.isGameOver()) {
            Map<Agent, List<Action>> plannedActions = new HashMap<>();

            for (Agent agent : agents) {
                Map<String, Boolean> goal = Map.of("scored", true);
                List<Action> plan = planner.plan(agent.state, goal, actions, agent.playerId, ggs);
                if (plan != null && !plan.isEmpty()) {
                    plannedActions.put(agent, plan);
                }
            }

            Collections.shuffle(agents);

            Set<String> puckTakers = new HashSet<>();
            for (Agent agent : agents) {
                List<Action> plan = plannedActions.get(agent);
                if (plan == null || plan.isEmpty()) {
                    continue;
                }

                Action action = plan.get(0);
                boolean performed = action.perform(agent, ggs, agents);
                if (performed) {
                    action.getEffects().forEach(agent.state::put);
                }

                if (action instanceof TakePuckAction && performed) {
                    puckTakers.add(agent.playerId);
                }
            }

            if (puckTakers.size() > 1) {
                String lucky = puckTakers.stream().skip(random.nextInt(puckTakers.size())).findFirst().orElse(null);
                ggs.puckHolderId = lucky;
                for (Agent a : agents) {
                    a.state.put("hasPuck", a.playerId.equals(lucky));
                }
            }
        }

        return new GameResult(ggs.team1Score, ggs.team2Score);
    }
}
