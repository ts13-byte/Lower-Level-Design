package PracticeProblems.ParkingLot.model;

public abstract class Vehicle {
    private String licensePlate;
    abstract VehicleType getType();

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }
}
