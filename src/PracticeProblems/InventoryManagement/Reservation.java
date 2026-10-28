package PracticeProblems.InventoryManagement;

import java.util.List;

/**
 * this class represents a reservation for a particular order.
 * starts with the active state which means that the order is only reserved to be either confirmed or released.
 * confirmed = actual deduction of stock
 * released = reserved stock is released.
 */
public class Reservation {
    private final String reservationId;
    private final List<OrderLine> orderLines;
    private ReservationStatus status = ReservationStatus.ACTIVE; // starts with the ACTIVE status for a newly created reservation.


    public Reservation(String reservationId, List<OrderLine> orderLines) {
        this.reservationId = reservationId;
        this.orderLines = List.copyOf(orderLines); // frozen snapshot
    }

    public String getReservationId() {
        return reservationId;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    /**
     * if the status is ACTIVE -> set it to next and return true otherwise false.
     * stops the double confirm or double release.
     * @param next
     * @return
     */
    public synchronized boolean tryComplete(ReservationStatus next) {
        // if reservation is already used(confirmed or released), do nothing and return.
        if(status != ReservationStatus.ACTIVE) return false;
        // stamp with the status the caller passes.
        status = next;
        return true;
    }
}
