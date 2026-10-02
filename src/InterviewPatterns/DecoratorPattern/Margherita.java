package InterviewPatterns.DecoratorPattern;

public class Margherita implements Pizza{
    @Override
    public String getDescription() {
        return "Base : Margherita";
    }

    @Override
    public double getCost() {
        return 150.0;
    }
}
