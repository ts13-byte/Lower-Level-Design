package PracticeProblems.BookMyShow;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

public class BookingSystem {
   private List<Theatre> theatres;
   private Map<String, Show> showIndex;
    private Map<String, String> showIdToTheatreName;

    public BookingSystem(List<Theatre> theatres) {
        this.theatres = theatres;
        this.showIndex = new HashMap<>();
        this.showIdToTheatreName = new HashMap<>();
        buildShowIndex();
    }

    private void buildShowIndex() {
        for(Theatre theatre : theatres) {
            for(Screen screen : theatre.getScreens()) {
                for(Show show : screen.getShows()) {
                    showIndex.put(show.getShowId(), show);
                    showIdToTheatreName.put(show.getShowId(), theatre.getTheatreName());
                }
            }
        }
    }

    private void releaseAll(List<Seat> seats) {
        for (Seat seat : seats) {
            seat.release();
        }
    }

    public Ticket bookSeats(User user , String showId , List<Integer> seatNumbers) {
        Show show = showIndex.get(showId);

        if(show == null) {
            throw new IllegalArgumentException("Show not found " + showId);
        }

        List<Seat> seatsToBook = findSeats(show , seatNumbers);
        List<Seat> lockedSeats = new ArrayList<>();

        for(Seat seat : seatsToBook) {
            if(seat.tryLock(user)) {
                lockedSeats.add(seat);
            } else {
                // CASE : if any of the requested seats are already locked from before , then release all the requested locked seats.
                releaseAll(lockedSeats);
                throw new SeatUnavailableException("Could not lock seat " + seat.getSeatNumber());
            }
        }

        // CASE : if payment fails release all the locked seats by user.
        if(!processPayment(user, lockedSeats)) {
            releaseAll(lockedSeats);
            throw new PaymentFailedException("Payment failed for show " + showId);
        }

        // CASE : success flow - user is able to lock the requested seats and payment succeeds
        for(Seat seat : lockedSeats) {
            seat.confirmBooking();
        }

        double totalPrice = lockedSeats.stream().mapToDouble(s -> s.getSeatType().getPrice()).sum();
        String theatreName = showIdToTheatreName.get(showId);
        return new Ticket(UUID.randomUUID().toString(), user, show, theatreName, lockedSeats, totalPrice, LocalDateTime.now());
    }

    private boolean processPayment(User user, List<Seat> seats) {
        return true;
    }


    private List<Seat> findSeats(Show show, List<Integer> seatNumbers) {
        List<Seat> result = new ArrayList<>();

        for(Integer seatNumber : seatNumbers) {
            Seat found = null;
            for(Seat seat : show.getSeats()) {
                if(seat.getSeatNumber() == seatNumber) {
                    found = seat;
                    break;
                }
            }

            if(found == null) {
                throw new IllegalArgumentException("Seat " + seatNumber + " does not exist for this show");
            }

            result.add(found);
        }

        return result;
    }


}
