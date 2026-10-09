package PracticeProblems.CricBuzz;

public class Innings {
    private int inningsNumber;
    private String battingTeam;
    private int score;
    private int wickets;
    private int ballsBowled;

    public Innings(int inningsNumber, String battingTeam) {
        this.inningsNumber = inningsNumber;
        this.battingTeam = battingTeam;
    }

    public int getInningsNumber() {
        return inningsNumber;
    }

    public void setInningsNumber(int inningsNumber) {
        this.inningsNumber = inningsNumber;
    }

    public String getBattingTeam() {
        return battingTeam;
    }

    public void setBattingTeam(String battingTeam) {
        this.battingTeam = battingTeam;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getWickets() {
        return wickets;
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public void setBallsBowled(int ballsBowled) {
        this.ballsBowled = ballsBowled;
    }


    public synchronized void recordBall(int runs, boolean isWicket) {
        score += runs;
        if(isWicket) wickets++;
        ballsBowled++;
    }

    public double getCurrentOver() {
        int overs = ballsBowled / 6;
        int ballsInCurrentOver = ballsBowled % 6;
        return overs + ballsInCurrentOver / 10.0;
    }
}
