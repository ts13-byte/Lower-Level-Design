package SOLIDPrinciples.OpenClosedPrinciple;

public class NotificationService {
    public void sendNotification(Order order , NotificationStrategy strategy) {
        strategy.sendMessage(order);
    }
}
