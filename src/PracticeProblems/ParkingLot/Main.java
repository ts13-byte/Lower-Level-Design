package PracticeProblems.ParkingLot;

import PracticeProblems.ParkingLot.exceptions.ParkingLotFullException;
import PracticeProblems.ParkingLot.model.*;
import PracticeProblems.ParkingLot.service.ParkingLotSystem;
import PracticeProblems.ParkingLot.strategy.HourlyPricingStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        // Setup
        ParkingSpotType carType = new ParkingSpotType("CAR-PARKING", VehicleType.CAR,50.0);
        ParkingSpotType truckType = new ParkingSpotType("TRUCK-PARKING" , VehicleType.TRUCK , 75.5);
        ParkingSpotType bikeType = new ParkingSpotType("BIKE-PARKING" , VehicleType.BIKE , 20.5);

        ParkingSpot spot1 = new ParkingSpot("S1", carType);
        ParkingSpot spot2 = new ParkingSpot("S2" , truckType);
        ParkingSpot spot3 = new ParkingSpot("S3" , bikeType);
        Floor floor1 = new Floor(List.of(spot1,spot2,spot3));
        ParkingLotSystem system = new ParkingLotSystem(List.of(floor1), new HourlyPricingStrategy());

        // Test: successful park
        Vehicle car = new Car("KA-01-1234");
        Ticket ticket = system.park(car);
        System.out.println("Parked successfully: " + ticket.getTicketId());

        // Test: park when full or when the type is not compatible
        try {
            Vehicle car2 = new Car("KA-01-5678");
            system.park(car2);
        } catch (ParkingLotFullException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }

        // Test: unpark and check price
        double price = system.unPark(ticket);
        System.out.println("Price charged: " + price);

        //Test : calculate Price
        Ticket manualTicket = new Ticket(UUID.randomUUID().toString(), car, spot1, LocalDateTime.now().minusHours(2).minusMinutes(10));
        manualTicket.setExitTime(LocalDateTime.now());
        double price2 = new HourlyPricingStrategy().calculatePrice(manualTicket);
        System.out.println("Price for 2h10m at rate 50: " + price2);
    }
}
