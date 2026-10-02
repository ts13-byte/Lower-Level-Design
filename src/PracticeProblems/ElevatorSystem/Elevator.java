package PracticeProblems.ElevatorSystem;

import java.util.Collections;
import java.util.concurrent.ConcurrentSkipListSet;

public class Elevator {
    private volatile int currentFloor;// written by the movement loop, read by threads adding requests
    private Direction currentDirection;
    private boolean doorsOpen;

    private ConcurrentSkipListSet<Integer> upRequests;
    private ConcurrentSkipListSet<Integer> downRequests;

    public Elevator(int startingFloor) {
        this.currentFloor = startingFloor;
        this.currentDirection = Direction.IDLE;
        this.doorsOpen = false;
        this.upRequests = new ConcurrentSkipListSet<>();
        this.downRequests = new ConcurrentSkipListSet<>();
    }

    /**
     * Adds new request into down and up concurrent sets , which will be picked by the elevator at the time of servicing.
     * @param request
     */
    public void addRequest(Request request) {
        Direction placement = request.resolveDirection(currentFloor);
        if(placement == Direction.DOWN) {
            downRequests.add(request.getFloor());
        } else if (placement == Direction.UP) {
            upRequests.add(request.getFloor());
        }
        // IDLE requests do not need to be entertained.
    }


    /**
     * stimulate the movement of elevator -> one tick of time passing
     * true = stop occured this tick
     * false = no stop occured this tick - either elevator just moved one floor or there is nothing to do at all.
     * @return boolean true/false
     */
    public boolean step() {
        // CASE : nothing to do at all , no up or down requests -> go idle.
        if(upRequests.isEmpty() && downRequests.isEmpty()) {
            currentDirection = Direction.IDLE;
            return false;
        }

        // CASE : if current direction is idle, decide which way elevator needs to move.
        if(currentDirection == Direction.IDLE) {
            currentDirection = upRequests.contains(currentFloor) ? Direction.UP
                    : downRequests.contains(currentFloor) ? Direction.DOWN
                    : anyPendingAbove() ? Direction.UP : Direction.DOWN;
        }
        // CASE : checks if it is currently on the floor it needs to service.
        if(stopHere()) return true;

        // CASE : check if there are more requests to be serviced in the direction it is moving
        // if yes - then move towards them.
        boolean moreAhead = currentDirection == Direction.UP ? anyPendingAbove() : anyPendingBelow();

        // CASE : If there are no more pending requests in the direction the elevator is moving
        // then reverse the direction of traversal
        if(!moreAhead) {
            currentDirection = (currentDirection == Direction.UP) ? Direction.DOWN : Direction.UP;
            // if the current floor is the one which needs to be serviced , stop here
            if(stopHere()) return true;
        }

        currentFloor += (currentDirection == Direction.UP) ? 1 : -1;
        return false;
    }

    /**
     * checks whether there are any requests worth travelling towards above it from both up and down sets
     * when the elevator was idle previously or below the floor from which requests were made.
     * example - elevator at 3 is idle , someone on 8th floor presses down button
     * elevator has to travel all the way up to pick them from 8th floor.
     * @return true/false
     */
    private boolean anyPendingAbove() {
        return upRequests.higher(currentFloor) != null || downRequests.higher(currentFloor) != null;
    }

    /**
     * checks whether there are any requests worth travelling towards below it from both up and down sets
     * when the elevator was idle previously or above the floor from which requests were made.
     * @return true/false
     */
    private boolean anyPendingBelow() {
        return upRequests.lower(currentFloor) != null || downRequests.lower(currentFloor) != null;
    }

    /**
     * Helps decide if we are stopping on a given current floor while going in a specific current direction
     * also removes the serviced request from the direction set.
     * @return true/false
     */
    private boolean stopHere() {
        // if it has not reached the current floor yet , no need to stop , return false.
        if(!pendingSet(currentDirection).remove(currentFloor)) return false;
        // otherwise , elevator stops at the current floor , opens door
        doorsOpen = true;
        System.out.println("Stopping at floor " + currentFloor + " (" + currentDirection + ")");
        doorsOpen = false;
        return true;
    }

    /**
     * returns the set containing the given direction
     * @param d
     * @return ConcurrentSkipListSet<Integer></>
     */
    private ConcurrentSkipListSet<Integer> pendingSet(Direction d) {
        return d == Direction.UP ? upRequests : downRequests;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }
}
