package PracticeProblems.ElevatorSystem;

public interface Request {
    int getFloor();
    Direction resolveDirection(int currentFloor);
}
