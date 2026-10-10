package PracticeProblems.ShoppingCart;

public class FlatStrategy implements DiscountStrategy{
    private final double amount;

    public FlatStrategy(double amount) {
        this.amount = amount;
    }

    @Override
    public double apply(double currentPrice) {
        return Math.max(0,currentPrice - amount);
    }
}
