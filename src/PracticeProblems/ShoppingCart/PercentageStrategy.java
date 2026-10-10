package PracticeProblems.ShoppingCart;

public class PercentageStrategy implements DiscountStrategy{

    private final double percent;

    public PercentageStrategy(double percent) {
        this.percent = percent;
    }

    @Override
    public double apply(double currentPrice) {
        return currentPrice * ( 1 - percent/100);
    }
}
