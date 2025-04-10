package com.hockeymanager.application.engine.models.goap;

public class GlobalGameState {
    public static boolean puckTaken = false;
    public static int team1Score = 0;
    public static int team2Score = 0;
    public static int puckOwnerTeam = 1;
    public static boolean gameEnded = false;

    public static void resetPuck(int scoringTeam) {
        puckTaken = false;
        puckOwnerTeam = (scoringTeam == 1) ? 2 : 1; // Donne la rondelle à l'équipe qui s'est fait scorer.
    }

    public static void scoreGoal(int team) {
        if (team == 1) {
            team1Score++;
        } else {
            team2Score++;
        }

        if (team1Score >= 10 || team2Score >= 10) { // Arrête la game après 10 buts, sinon je n'avais pas de condition
                                                    // d'arrêt (pour l'instant)
            gameEnded = true;
        }

        resetPuck(team);
    }

    public static void resetGame() {
        puckTaken = false;
        team1Score = 0;
        team2Score = 0;
        puckOwnerTeam = 1;
        gameEnded = false;
    }
}
