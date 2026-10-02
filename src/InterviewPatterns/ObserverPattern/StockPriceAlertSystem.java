package InterviewPatterns.ObserverPattern;

public class StockPriceAlertSystem {
    public static void main(String[] args) {
        Stock stock1 = new Stock("stock-1");
        Stock stock2 = new Stock("stock-2");

        UserAlert userAlert1 = new UserAlert("user-1");
        UserAlert userAlert2 = new UserAlert("user-2");
        UserAlert userAlert3 = new UserAlert("user-3");

        PriceThresholdAlert priceThresholdAlert = new PriceThresholdAlert(25.5);

        stock1.attach(userAlert1);
        stock1.attach(userAlert3);
        stock2.attach(userAlert2);
        stock2.attach(userAlert1);
        stock2.attach(userAlert3);
        stock1.attach(priceThresholdAlert);
        stock2.attach(priceThresholdAlert);

        stock1.setPrice(24);
        stock2.setPrice(30);

        stock1.detach(userAlert3);
        stock1.setPrice(65.5);
    }
}
