package com.hockeymanager.application.engine.models.goap;

public class GlobalGameState {
    public boolean puckTaken = false;
    public int team1Score = 0;
    public int team2Score = 0;
    public int puckOwnerTeam = 1;
    public boolean gameEnded = false;
    public String puckHolderId = null;
    public int team1Shots = 0;
    public int team2Shots = 0;
    public final int MAX_SHOTS = 2;

    public synchronized void resetPuck(int scoringTeam) {
        puckTaken = false;
        puckHolderId = null;
        puckOwnerTeam = (scoringTeam == 1) ? 2 : 1;
    }

    public synchronized void scoreGoal(int team) {
        if (team == 1)
            team1Score++;
        else
            team2Score++;
        resetPuck(team);
    }

    public boolean isGameOver() {
        return team1Shots >= MAX_SHOTS || team2Shots >= MAX_SHOTS;
    }
}