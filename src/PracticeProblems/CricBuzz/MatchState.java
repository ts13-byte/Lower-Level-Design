package PracticeProblems.CricBuzz;

public class MatchState {
    private final String matchId;
    private final int currentScore;
    private final int wickets;
    private final double currentOver;
    private final int inningsNumber;
    private final String battingTeam;

    public MatchState(String matchId, int currentScore, int wickets, double currentOver, int inningsNumber, String battingTeam) {
        this.matchId = matchId;
        this.currentScore = currentScore;
        this.wickets = wickets;
        this.currentOver = currentOver;
        this.inningsNumber = inningsNumber;
        this.battingTeam = battingTeam;
    }

    public String getMatchId() {
        return matchId;
    }

    public int getCurrentScore() {
        return currentScore;
    }

    public int getWickets() {
        return wickets;
    }

    public double getCurrentOver() {
        return currentOver;
    }

    public int getInningsNumber() {
        return inningsNumber;
    }

    public String getBattingTeam() {
        return battingTeam;
    }
}
