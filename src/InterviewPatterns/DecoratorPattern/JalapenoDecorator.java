package InterviewPatterns.DecoratorPattern;

public class JalapenoDecorator extends PizzaDecorator{

    public JalapenoDecorator(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return pizza.getDescription() + " + Jalapenos ";
    }

    @Override
    public double getCost() {
        return pizza.getCost() + 15.0;
    }
}
