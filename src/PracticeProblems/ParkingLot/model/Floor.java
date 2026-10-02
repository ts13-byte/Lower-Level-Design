package PracticeProblems.ParkingLot.model;

import java.util.List;

public class Floor {
    private List<ParkingSpot> parkingSpots;

    public Floor(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public void setParkingSpots(List<ParkingSpot> parkingSpots) {
        this.parkingSpots = parkingSpots;
    }

    public boolean isFull() {
        for(ParkingSpot spot : parkingSpots) {
            if(!spot.isOccupied()) {
                return false;
            }
        }

        return true;
    }
}
