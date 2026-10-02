package PracticeProblems.ParkingLot.strategy;

import PracticeProblems.ParkingLot.model.Ticket;

import java.time.Duration;

public class HourlyPricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(Ticket ticket) {
        Duration duration = Duration.between(ticket.getEntryTime(), ticket.getExitTime());
        // gives the whole total minutes elapsed
        long totalMinutes = duration.toMinutes();

        // floating point division so that if 105/60 -> gives 1.75 then we ceil it to 2 hours total.
        long billableHours = (long)Math.ceil(totalMinutes / 60.0);
        if (billableHours == 0) {
            billableHours = 1; // minimum charge — even a few minutes counts as 1 hour
        }

        double rate = ticket.getParkingSpot().getParkingSpotType().getRate();
        return billableHours * rate;
    }
}
