package InterviewPatterns.FactoryPattern;

public class Bike extends Vehicle{

    Bike(String model, double dailyRentalRate) {
        super(model, dailyRentalRate);
    }

    @Override
    public double calculateTotalRentalRate(int days) {
        double baseAmount = days * dailyRentalRate;
        double taxAmount = baseAmount * VehicleTaxEnum.BIKE.getTaxPercentage()/100;
        return baseAmount + taxAmount;
    }
}
