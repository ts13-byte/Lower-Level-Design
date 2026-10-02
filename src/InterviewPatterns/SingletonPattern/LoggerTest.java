package InterviewPatterns.SingletonPattern;

public class LoggerTest {
    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        PaymentService paymentService = new PaymentService();
        orderService.placeOrder();
        paymentService.processPayment();
    }
}
