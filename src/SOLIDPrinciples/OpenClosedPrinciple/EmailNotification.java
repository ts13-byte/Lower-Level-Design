package SOLIDPrinciples.OpenClosedPrinciple;

public class EmailNotification implements NotificationStrategy{

    @Override
    public void sendMessage(Order order) {
        System.out.println("sending message via Email" + order.getOrderId());
    }
}
