package InterviewPatterns.FactoryPattern;

public class RentalService {
    public void rentVehicle(String type, String model, double dailyRate, int days) {
        Vehicle vehicle = VehicleFactory.getVehicle(type,model,dailyRate);
        vehicle.displayInfo();
        System.out.println("Total cost for " + days + " days: " + vehicle.calculateTotalRentalRate(days));
    }
}
