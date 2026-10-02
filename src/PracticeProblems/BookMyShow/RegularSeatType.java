package PracticeProblems.BookMyShow;

public class RegularSeatType extends SeatType{
    @Override
    double getPrice() {
        return 100.0;
    }

    @Override
    String getName() {
        return "Regular Seat";
    }
}
