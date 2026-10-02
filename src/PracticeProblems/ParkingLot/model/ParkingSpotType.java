package PracticeProblems.ParkingLot.model;

public class ParkingSpotType {
    private String parkingSpotTypeName;
    private VehicleType compatibleVehicleType;
    private double rate;

    public ParkingSpotType(String parkingSpotTypeName, VehicleType compatibleVehicleType, double rate) {
        this.parkingSpotTypeName = parkingSpotTypeName;
        this.compatibleVehicleType = compatibleVehicleType;
        this.rate = rate;
    }

    public String getParkingSpotTypeName() {
        return parkingSpotTypeName;
    }

    public void setParkingSpotTypeName(String parkingSpotTypeName) {
        this.parkingSpotTypeName = parkingSpotTypeName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public VehicleType getCompatibleVehicleType() {
        return compatibleVehicleType;
    }

    public void setCompatibleVehicleType(VehicleType compatibleVehicleType) {
        this.compatibleVehicleType = compatibleVehicleType;
    }
}
