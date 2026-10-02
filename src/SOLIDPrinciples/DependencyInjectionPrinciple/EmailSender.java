package SOLIDPrinciples.DependencyInjectionPrinciple;

public class EmailSender implements MessageSender{

    @Override
    public void sendMessage(String message) {
        System.out.println("Sending e-mail message" + message);
    }
}
