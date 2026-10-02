package SOLIDPrinciples.OpenClosedPrinciple;

import java.util.List;

public class NotificationServiceTest {
    public static void main(String[] args) {
        Order order = new Order("order-1234" , List.of("item1" , "item2" , "item3") , 74.25);
        NotificationService notificationService = new NotificationService();
        notificationService.sendNotification(order , new PushNotification());
    }
}
