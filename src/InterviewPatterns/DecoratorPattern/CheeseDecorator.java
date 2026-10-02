package InterviewPatterns.DecoratorPattern;

public class CheeseDecorator extends PizzaDecorator {

    public CheeseDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + Cheese ";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 30.0;
    }
}
