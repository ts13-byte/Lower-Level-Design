package InterviewPatterns.FactoryPattern;

public class Truck extends Vehicle{
    Truck(String model, double dailyRentalRate) {
        super(model, dailyRentalRate);
    }

    @Override
    public double calculateTotalRentalRate(int days) {
        double baseAmount = days * dailyRentalRate;
        double taxAmount = baseAmount * VehicleTaxEnum.TRUCK.getTaxPercentage()/100;
        return baseAmount + taxAmount;
    }
}
