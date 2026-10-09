package PracticeProblems.CricBuzz;

public class ScoreBoardDisplay implements Observer {
    @Override
    public void update(MatchState matchState) {
        System.out.println("[SCOREBOARD] " + matchState.getBattingTeam() + ": "
                + matchState.getCurrentScore() + "/" + matchState.getWickets());
    }
}
