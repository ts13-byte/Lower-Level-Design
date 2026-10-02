package PracticeProblems.ParkingLot.service;

import PracticeProblems.ParkingLot.exceptions.ParkingLotFullException;
import PracticeProblems.ParkingLot.model.Floor;
import PracticeProblems.ParkingLot.model.ParkingSpot;
import PracticeProblems.ParkingLot.model.Ticket;
import PracticeProblems.ParkingLot.model.Vehicle;
import PracticeProblems.ParkingLot.strategy.PricingStrategy;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ParkingLotSystem {
    private final List<Floor> floors;
    private final PricingStrategy pricingStrategy;


    public ParkingLotSystem(List<Floor> floors, PricingStrategy pricingStrategy) {
        this.floors = floors;
        this.pricingStrategy = pricingStrategy;
    }

    public Ticket park(Vehicle vehicle) {
        for(Floor floor : floors) {
            for(ParkingSpot parkingSpot : floor.getParkingSpots()) {
                if(parkingSpot.canFit(vehicle)) { // cheap-precheck
                    Ticket ticket = new Ticket(UUID.randomUUID().toString() , vehicle , parkingSpot , LocalDateTime.now());
                    if(parkingSpot.tryOccupy(vehicle , ticket)) { //atomic check
                        return ticket;
                    }
                    // else - another thread beat us to the booking - keep scanning.
                }
            }
        }
        throw new ParkingLotFullException("All parking slots are unavailable right now !");
    }


    public double unPark(Ticket ticket) {
        // for the case someone calls unPark twice on the same ticket.
        if (ticket.getExitTime() != null) {
            throw new IllegalStateException("This ticket has already been used to exit.");
        }
        ticket.setExitTime(LocalDateTime.now());
        ParkingSpot occupiedSpot = ticket.getParkingSpot();
        occupiedSpot.vacate();
        return pricingStrategy.calculatePrice(ticket);
    }


}
