package PracticeProblems.BookMyShow;

import java.util.List;

public class Screen {
    private int screenId;
    private int theatreId;
    private List<Show> shows;

    public Screen(int screenId, int theatreId, List<Show> shows) {
        this.screenId = screenId;
        this.theatreId = theatreId;
        this.shows = shows;
    }

    public int getScreenId() {
        return screenId;
    }

    public void setScreenId(int screenId) {
        this.screenId = screenId;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public List<Show> getShows() {
        return shows;
    }

    public void setShows(List<Show> shows) {
        this.shows = shows;
    }
}
