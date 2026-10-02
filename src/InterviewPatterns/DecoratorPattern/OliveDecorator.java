package InterviewPatterns.DecoratorPattern;

public class OliveDecorator extends PizzaDecorator{

    public OliveDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + Olives ";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 20.0;
    }
}
