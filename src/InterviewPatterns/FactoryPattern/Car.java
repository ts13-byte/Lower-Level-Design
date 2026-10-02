package InterviewPatterns.FactoryPattern;

public class Car extends Vehicle{

    Car(String model, double dailyRentalRate) {
        super(model, dailyRentalRate);
    }

    @Override
    public double calculateTotalRentalRate(int days) {
        double baseAmount = days * dailyRentalRate;
        double taxAmount = baseAmount * VehicleTaxEnum.CAR.getTaxPercentage()/100;
        return baseAmount + taxAmount;
    }
}
