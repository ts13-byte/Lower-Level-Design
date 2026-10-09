package PracticeProblems.CricBuzz;

public class User implements Observer {
    private String userId;
    private String name;

    public User(String userId, String name) {
        this.userId = userId;
        this.name = name;
    }

    @Override
    public void update(MatchState matchState) {
        System.out.println(name + " notified: " + matchState.getBattingTeam() + " — "
                + matchState.getCurrentScore() + "/" + matchState.getWickets()
                + " (" + matchState.getCurrentOver() + " overs)");
    }
}