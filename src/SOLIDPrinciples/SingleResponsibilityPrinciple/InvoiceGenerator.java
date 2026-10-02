package SOLIDPrinciples.SingleResponsibilityPrinciple;

public class InvoiceGenerator {
    public String generateInvoice(Order order) {
        return "Invoice for order: " + order.getOrderId() + " | Amount: " + order.getTotalAmount();
    }
}
