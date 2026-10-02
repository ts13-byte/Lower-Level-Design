package InterviewPatterns.DecoratorPattern;

public class Farmhouse implements Pizza{
    @Override
    public String getDescription() {
        return "Base : Farmhouse";
    }

    @Override
    public double getCost() {
        return 200.0;
    }
}
