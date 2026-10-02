package SOLIDPrinciples.OpenClosedPrinciple;

public class PushNotification implements NotificationStrategy{
    @Override
    public void sendMessage(Order order) {
        System.out.println("sending message via Push Notification" + order.getOrderId());
    }
}
