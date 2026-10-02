package SOLIDPrinciples.OpenClosedPrinciple;

public class SmsNotification implements NotificationStrategy{

    @Override
    public void sendMessage(Order order) {
        System.out.println("sending sms for orderId " + order.getOrderId());
    }
}
