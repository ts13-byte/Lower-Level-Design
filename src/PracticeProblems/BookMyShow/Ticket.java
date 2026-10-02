package PracticeProblems.BookMyShow;

import java.time.LocalDateTime;
import java.util.List;

public class Ticket {
    private String ticketId;
    private User user;
    private Show show;
    private String theatreName;
    private List<Seat> seats; // seats booked
    private double totalPrice;
    private LocalDateTime bookedAt;

    public Ticket(String ticketId, User user, Show show, String theatreName, List<Seat> seats, double totalPrice, LocalDateTime bookedAt) {
        this.ticketId = ticketId;
        this.user = user;
        this.show = show;
        this.theatreName = theatreName;
        this.seats = seats;
        this.totalPrice = totalPrice;
        this.bookedAt = bookedAt;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Show getShow() {
        return show;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public LocalDateTime getBookedAt() {
        return bookedAt;
    }

    public void setBookedAt(LocalDateTime bookedAt) {
        this.bookedAt = bookedAt;
    }
}
