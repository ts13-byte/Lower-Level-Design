package PracticeProblems.BookMyShow;

import java.time.Duration;
import java.time.LocalDateTime;

public class Seat {
    private int seatNumber;
    private SeatType seatType;
    private BookingStatus bookingStatus;
    private LocalDateTime lockedAt;

    public Seat(int seatNumber, SeatType seatType) {
        this.seatNumber = seatNumber;
        this.seatType = seatType;
        this.bookingStatus = BookingStatus.AVAILABLE;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public SeatType getSeatType() {
        return seatType;
    }

    public void setSeatType(SeatType seatType) {
        this.seatType = seatType;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getLockedAt() {
        return lockedAt;
    }

    public void setLockedAt(LocalDateTime lockedAt) {
        this.lockedAt = lockedAt;
    }

    /**
     * Tries to lock a seat for a user for 5 minutes.
     * @param user
     * @return true/false
     */
    public synchronized boolean tryLock(User user) {
        //CASE : seat was locked previously , but lock has expired now(stale lock scenario)
        if(bookingStatus == BookingStatus.LOCKED && isLockExpired()) {
            bookingStatus = BookingStatus.AVAILABLE;
        }
        //CASE : seat is either booked or still locked.
        if(bookingStatus != BookingStatus.AVAILABLE) {
            return false;
        }
        // CASE : otherwise lock it for the user
        bookingStatus = BookingStatus.LOCKED;
        lockedAt = LocalDateTime.now();
        return true;
    }

    /**
     * checks if a locked seat's lock has expired or not
     * @return true/false
     */
    private boolean isLockExpired() {
        return lockedAt != null && Duration.between(lockedAt, LocalDateTime.now()).toMinutes() >= 5;
    }

    public void release() {
        bookingStatus = BookingStatus.AVAILABLE;
        lockedAt = null;
    }

    public synchronized void confirmBooking() {
        bookingStatus = BookingStatus.BOOKED;
        lockedAt = null;
    }
}
