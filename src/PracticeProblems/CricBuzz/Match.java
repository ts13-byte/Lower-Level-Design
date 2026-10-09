package PracticeProblems.CricBuzz;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Match implements Subject {

    private final String matchId;
    private final Innings currentInnings;
    private final List<Observer> observerList = new CopyOnWriteArrayList<>(); // to avoid concurrent modification exception when the iterator is moving on the list of observers.

    public Match(String matchId, Innings currentInnings) {
        this.matchId = matchId;
        this.currentInnings = currentInnings;
    }

    @Override
    public void subscribe(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void unsubscribe(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers() {
        MatchState matchStateSnapShot = buildCurrentState();
        for(Observer observer : observerList) {
            observer.update(matchStateSnapShot);
        }
    }

    private MatchState buildCurrentState() {
        return new MatchState(matchId, currentInnings.getScore(), currentInnings.getWickets(),
                currentInnings.getCurrentOver(), currentInnings.getInningsNumber(), currentInnings.getBattingTeam());
    }

    /**
     * Gets information from live match about a ball bowled , and updates the observers.
     * @param runs
     * @param isWicket
     */
    public synchronized void recordBall(int runs, boolean isWicket) {
        currentInnings.recordBall(runs, isWicket);
        notifyObservers();
    }
}
