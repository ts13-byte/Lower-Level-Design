package PracticeProblems.ParkingLot.model;

public class Truck extends Vehicle{
    @Override
    VehicleType getType() {
        return VehicleType.TRUCK;
    }
}
