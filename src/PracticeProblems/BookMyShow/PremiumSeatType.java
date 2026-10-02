package PracticeProblems.BookMyShow;

public class PremiumSeatType extends SeatType{
    @Override
    double getPrice() {
        return 200.0;
    }

    @Override
    String getName() {
        return "Premium Seat";
    }
}
