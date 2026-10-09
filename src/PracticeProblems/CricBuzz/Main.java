package PracticeProblems.CricBuzz;

public class Main {
    public static void main(String[] args) {
                Innings innings1 = new Innings(1, "India");
                Match match1 = new Match("M1", innings1);

                Innings innings2 = new Innings(1, "Australia");
                Match match2 = new Match("M2", innings2);

                User alice = new User("U1", "Alice");
                User bob = new User("U2", "Bob");
                ScoreBoardDisplay scoreboard = new ScoreBoardDisplay();

                // Alice follows both matches; Bob and the scoreboard only follow match1
                match1.subscribe(alice);
                match1.subscribe(bob);
                match1.subscribe(scoreboard);
                match2.subscribe(alice);

                System.out.println("--- Match 1: ball by ball ---");
                match1.recordBall(4, false);
                match1.recordBall(1, false);
                match1.recordBall(0, true);
                match1.recordBall(6, false);

                System.out.println("--- Match 2: ball by ball (only Alice watching) ---");
                match2.recordBall(2, false);
                match2.recordBall(0, true);

                System.out.println("--- Bob unsubscribes from match1, further balls should not notify him ---");
                match1.unsubscribe(bob);
                match1.recordBall(4, false);
    }
}


