package InterviewPatterns.StrategyPattern;

public class PaymentService {
    private PaymentStrategy paymentStrategy;

    PaymentService(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void setPaymentStrategy(PaymentStrategy paymentStrategy) {
        this.paymentStrategy = paymentStrategy;
    }

    public void payAmount(double amount) {
        paymentStrategy.pay(amount);
    }
}
