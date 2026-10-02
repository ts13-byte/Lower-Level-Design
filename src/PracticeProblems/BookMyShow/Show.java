package PracticeProblems.BookMyShow;

import java.time.LocalDateTime;
import java.util.List;

public class Show {

    private String showId;
    private String showName;
    private int screenId;
    private List<Seat> seats;
    private LocalDateTime startsAt;
    private LocalDateTime endsAt;

    public Show(String showId, String showName, int screenId, List<Seat> seats, LocalDateTime startsAt, LocalDateTime endsAt) {
        this.showId = showId;
        this.showName = showName;
        this.screenId = screenId;
        this.seats = seats;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }

    public String getShowId() {
        return showId;
    }

    public void setShowId(String showId) {
        this.showId = showId;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public LocalDateTime getStartsAt() {
        return startsAt;
    }

    public void setStartsAt(LocalDateTime startsAt) {
        this.startsAt = startsAt;
    }

    public LocalDateTime getEndsAt() {
        return endsAt;
    }

    public void setEndsAt(LocalDateTime endsAt) {
        this.endsAt = endsAt;
    }
}
