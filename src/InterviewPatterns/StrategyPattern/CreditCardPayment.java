package InterviewPatterns.StrategyPattern;

public class CreditCardPayment implements PaymentStrategy{
    private String cardNumber;
    private String cvv;

    CreditCardPayment(String cardNumber , String cvv) {
        this.cardNumber = cardNumber;
        this.cvv = cvv;
    }

    @Override
    public void pay(double amount) {
        System.out.println("paying " + amount + " via card number " + amount);
    }
}
