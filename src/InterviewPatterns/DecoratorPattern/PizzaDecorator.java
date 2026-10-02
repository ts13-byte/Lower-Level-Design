package InterviewPatterns.DecoratorPattern;

public abstract class PizzaDecorator implements Pizza{
    protected Pizza pizza;

    public PizzaDecorator(Pizza pizza) {
         this.pizza = pizza;
    }
}
