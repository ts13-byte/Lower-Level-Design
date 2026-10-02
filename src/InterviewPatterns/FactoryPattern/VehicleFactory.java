package InterviewPatterns.FactoryPattern;

public class VehicleFactory {
    public static Vehicle getVehicle(String type , String model , double dailyRate) {
        switch(type) {
            case "CAR" : return new Car(model,dailyRate);
            case "BIKE" : return new Bike(model , dailyRate);
            case "TRUCK" : return new Truck(model,dailyRate);
            default: throw new IllegalArgumentException("unknown type " + type);
        }
    }
}
