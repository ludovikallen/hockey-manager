package com.hockeymanager.application.engine.models.goap;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Planner {
    public List<Action> plan(PlayerGameState initial, Map<String, Boolean> goal, List<Action> actions, String playerId,
            GlobalGameState ggs) {
        Queue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(n -> n.cost));
        open.add(new Node(null, null, initial, 0));

        while (!open.isEmpty()) {
            Node current = open.poll();
            if (goal.entrySet().stream()
                    .allMatch(e -> current.state.getOrDefault(e.getKey(), false).equals(e.getValue()))) {
                return buildPlan(current);
            }

            for (Action action : actions) {
                if (action.isApplicable(current.state)
                        && action.checkProceduralPrecondition(current.state, ggs, playerId)) {
                    PlayerGameState newState = current.state.copy();
                    action.getEffects().forEach(newState::put);
                    Node newNode = new Node(action, current, newState, current.cost + action.getCost());
                    open.add(newNode);
                }
            }
        }
        return null;
    }

    private List<Action> buildPlan(Node node) {
        LinkedList<Action> plan = new LinkedList<>();
        while (node != null && node.action != null) {
            plan.addFirst(node.action);
            node = node.parent;
        }
        return plan;
    }

    private static class Node {
        Action action;
        Node parent;
        PlayerGameState state;
        float cost;

        Node(Action action, Node parent, PlayerGameState state, float cost) {
            this.action = action;
            this.parent = parent;
            this.state = state;
            this.cost = cost;
        }
    }
}