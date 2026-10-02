package PracticeProblems.ElevatorSystem;

public class ExternalRequest implements Request{

    private int floor;
    private Direction direction;

    public ExternalRequest(int floor, Direction direction) {
        if(direction == Direction.IDLE) {
            throw new IllegalArgumentException("An external request must be up or down !!");
        }
        this.floor = floor;
        this.direction = direction;
    }

    @Override
    public int getFloor() {
        return floor;
    }

    @Override
    public Direction resolveDirection(int currentFloor) {
        return direction;
    }

    public Direction getDirection() {
        return direction;
    }
}
