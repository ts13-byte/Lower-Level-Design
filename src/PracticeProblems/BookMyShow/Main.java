package PracticeProblems.BookMyShow;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Seat s1 = new Seat(1, new RegularSeatType());
        Seat s2 = new Seat(2, new RegularSeatType());
        Seat s3 = new Seat(3, new PremiumSeatType());
        Seat s4 = new Seat(4, new PremiumSeatType());

        Show show = new Show("SH1", "Oppenheimer", 101,
                List.of(s1, s2, s3), LocalDateTime.now(), LocalDateTime.now().plusHours(3));
        Screen screen = new Screen(101, 1, List.of(show));
        Theatre theatre = new Theatre(1, "PVR Cineplex", List.of(screen));

        BookingSystem system = new BookingSystem(List.of(theatre));

        User alice = new User("U1", "Alice");
        User bob = new User("U2", "Bob");
        User caroline = new User("U3" , "Caroline");
        User dave = new User("U4" , "Dave");

        // Test 1: successful booking
        Ticket ticket = system.bookSeats(alice, "SH1", List.of(1, 2));
        System.out.println("Booked: " + ticket.getTicketId() + " at " + ticket.getTheatreName() + ", total: " + ticket.getTotalPrice());

        // Test 2: Bob tries to book seat 1 (already booked) — should fail
        try {
            system.bookSeats(bob, "SH1", List.of(1, 3));
            System.out.println("ERROR: should have thrown!");
        } catch (SeatUnavailableException e) {
            System.out.println("Correctly rejected: " + e.getMessage());
        }

        // Test 3: confirm seat 3 is still available (not stuck locked from Bob's failed attempt)
        Ticket ticket2 = system.bookSeats(bob, "SH1", List.of(3));
        System.out.println("Bob booked: " + ticket2.getTicketId() + ", total: " + ticket2.getTotalPrice());

        //Test 4 : concurrent booking scenario(a user has locked a seat before but after 5 mins released the lock)
        s4.tryLock(caroline);
        s4.setLockedAt(s4.getLockedAt().minusMinutes(6));
        boolean isLockedByDave = s4.tryLock(dave);

        if(isLockedByDave) {
            System.out.println("Seat successfully locked by Dave after 5 minutes");
        } else {
            System.out.println("Seat unable to be locked by Dave after 5 minutes");
        }


    }
}