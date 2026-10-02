package InterviewPatterns.StrategyPattern;

public class PaymentServiceTest {
    public static void main(String[] args) {
        PaymentService paymentService = new PaymentService(new CreditCardPayment("124234" , "548"));
        paymentService.payAmount(100.25);
        paymentService.setPaymentStrategy(new UpiPayment("74578534"));
        paymentService.payAmount(90.00);
    }
}
