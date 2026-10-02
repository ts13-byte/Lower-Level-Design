package InterviewPatterns.FactoryPattern;

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService rentalService = new RentalService();
        rentalService.rentVehicle("CAR" , "abc" , 24.5 , 3);
    }
}
