package SOLIDPrinciples.SingleResponsibilityPrinciple;

public class EmailService {
        public void sendConfirmationEmail(Order order) {
            System.out.println("Sending email for order: " + order.getOrderId());
        }
}
