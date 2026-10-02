package InterviewPatterns.ObserverPattern;

public class UserAlert implements Observer{

    private String userName;

    UserAlert(String userName) {
        this.userName = userName;
    }

    @Override
    public void update(String stockName, double stockPrice) {
        System.out.println(userName + " your stock " + stockName + " has been updated to price " + stockPrice);
    }
}
