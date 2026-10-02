package InterviewPatterns.ObserverPattern;

public class PriceThresholdAlert implements Observer{
    private double threshold;

    PriceThresholdAlert(double threshold) {
        this.threshold = threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    @Override
    public void update(String stockName, double stockPrice) {
        if(stockPrice > threshold) {
            System.out.println("Stock " + stockName + " passed the threshold of " + threshold + " with the new price " + stockPrice);
        }
    }
}
