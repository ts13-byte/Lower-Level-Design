package SOLIDPrinciples.SingleResponsibilityPrinciple;

public class OrderRepository {
    public void saveOrder(Order order) {
        System.out.println("Saving order " + order.getOrderId() + " to database");
    }
}
