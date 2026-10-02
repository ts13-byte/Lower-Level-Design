package InterviewPatterns.StrategyPattern;

public class UpiPayment implements PaymentStrategy{
    private String upiId;

    UpiPayment(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("paying " + amount + " via upi id " + upiId);
    }
}
