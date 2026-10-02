package InterviewPatterns.DecoratorPattern;

public class MushroomsDecorator extends PizzaDecorator {

    public MushroomsDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + Mushrooms ";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 25.0;
    }
}
