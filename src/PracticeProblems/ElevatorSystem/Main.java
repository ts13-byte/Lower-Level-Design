package PracticeProblems.ElevatorSystem;

public class Main {
    public static void main(String[] args) {
        Elevator e = new Elevator(1);
        e.addRequest(new ExternalRequest(4, Direction.UP));   // A
        e.addRequest(new ExternalRequest(6, Direction.DOWN)); // B
        e.addRequest(new ExternalRequest(2, Direction.UP));   // C

        advanceToNextStop(e);                 // C boards at 2
        e.addRequest(new InternalRequest(5)); // C presses 5
        advanceToNextStop(e);                 // A boards at 4
        e.addRequest(new InternalRequest(8)); // A presses 8
        advanceToNextStop(e);                 // C exits at 5
        advanceToNextStop(e);                 // A exits at 8, floor 6 skipped
        advanceToNextStop(e);                 // B boards at 6, going down
        e.addRequest(new InternalRequest(1)); // B presses 1
        advanceToNextStop(e);                 // B exits at 1

        e.step();
        System.out.println("Direction now: " + e.getCurrentDirection());

        // idle at 3, only request is above and wants to go down
        Elevator e2 = new Elevator(3);
        e2.addRequest(new ExternalRequest(8, Direction.DOWN));
        advanceToNextStop(e2);
    }

    private static void advanceToNextStop(Elevator e) {
        while (!e.step()) {
            if (e.getCurrentDirection() == Direction.IDLE) return;
        }
    }
}
