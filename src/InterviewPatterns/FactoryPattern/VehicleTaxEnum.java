package InterviewPatterns.FactoryPattern;

public enum VehicleTaxEnum {

    CAR(18.0),

    BIKE(12.0),

    TRUCK(28.0),

    BUS(25.0);

    private final double taxPercentage;

    VehicleTaxEnum(double taxPercentage) {

        this.taxPercentage = taxPercentage;

    }

    public double getTaxPercentage() {

        return taxPercentage;

    }
}
