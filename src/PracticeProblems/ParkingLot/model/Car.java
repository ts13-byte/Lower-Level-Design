package PracticeProblems.ParkingLot.model;

public class Car extends Vehicle{
    public Car(String s) {
        super();
    }

    @Override
    VehicleType getType() {
        return VehicleType.CAR;
    }
}
