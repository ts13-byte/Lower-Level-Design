package SOLIDPrinciples.DependencyInjectionPrinciple;

public class NotificationServiceTest {
    public static void main(String[] args) {
        NotificationService notificationService = new NotificationService(new SmsSender());
        notificationService.sendNotification("hello world!!");
    }
}
