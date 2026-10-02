package PracticeProblems.BookMyShow;

import java.util.List;

public class Theatre {
    private int theatreId;
    private String theatreName;
    private List<Screen> screens;

    public Theatre(int theatreId, String theatreName, List<Screen> screens) {
        this.theatreId = theatreId;
        this.theatreName = theatreName;
        this.screens = screens;
    }

    public int getTheatreId() {
        return theatreId;
    }

    public void setTheatreId(int theatreId) {
        this.theatreId = theatreId;
    }

    public String getTheatreName() {
        return theatreName;
    }

    public void setTheatreName(String theatreName) {
        this.theatreName = theatreName;
    }

    public List<Screen> getScreens() {
        return screens;
    }

    public void setScreens(List<Screen> screens) {
        this.screens = screens;
    }
}
