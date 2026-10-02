package PracticeProblems.ParkingLot.strategy;

import PracticeProblems.ParkingLot.model.Ticket;

public interface PricingStrategy {
    public double calculatePrice(Ticket ticket);
}
