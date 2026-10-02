package PracticeProblems.ElevatorSystem;

public class InternalRequest implements Request{
    private int floor;

    public InternalRequest(int floor) {
        this.floor = floor;
    }

    @Override
    public int getFloor() {
        return floor;
    }

    @Override
    public Direction resolveDirection(int currentFloor) {
        if(floor > currentFloor) return Direction.UP;
        else if(floor < currentFloor) return Direction.DOWN;
        return Direction.IDLE;
    }


}
