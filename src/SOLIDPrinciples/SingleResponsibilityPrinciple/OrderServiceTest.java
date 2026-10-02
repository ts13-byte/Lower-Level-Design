package SOLIDPrinciples.SingleResponsibilityPrinciple;

import java.util.List;

public class OrderServiceTest {
    public static void main(String[] args) {
        Order order = new Order("order-1234" , List.of("item1" , "item2" , "item3") , 74.25);
        OrderRepository orderRepository = new OrderRepository();
        orderRepository.saveOrder(order);

        InvoiceGenerator invoiceGenerator = new InvoiceGenerator();
        System.out.println(invoiceGenerator.generateInvoice(order));

        EmailService emailService  = new EmailService();
        emailService.sendConfirmationEmail(order);
    }
}
