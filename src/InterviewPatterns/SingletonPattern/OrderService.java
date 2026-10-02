package InterviewPatterns.SingletonPattern;

public class OrderService {

    public void placeOrder() {
        Logger logger = Logger.getInstance();
        logger.logInfo("order placed");
    }
}
