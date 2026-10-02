package InterviewPatterns.FactoryPattern;

public abstract class Vehicle {
    protected String model;
    protected double dailyRentalRate;

    Vehicle(String model , double dailyRentalRate) {
        this.model = model;
        this.dailyRentalRate = dailyRentalRate;
    }

    public void displayInfo() {
        System.out.println("Type: " + getClass().getSimpleName() + " | Model: " + model + " | Rate/day: " + dailyRentalRate);
    }

    public abstract double calculateTotalRentalRate(int days);

}
