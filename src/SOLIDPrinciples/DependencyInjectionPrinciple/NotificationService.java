package SOLIDPrinciples.DependencyInjectionPrinciple;

public class NotificationService {
    private MessageSender messageSender;

    NotificationService(MessageSender messageSender) {
        this.messageSender = messageSender;
    }

    public void sendNotification(String message) {
        messageSender.sendMessage(message);
    }
}
