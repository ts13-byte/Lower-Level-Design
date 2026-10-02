package SOLIDPrinciples.DependencyInjectionPrinciple;

public class SmsSender implements MessageSender{

    @Override
    public void sendMessage(String message) {
        System.out.println("sending sms message " + message);
    }
}
