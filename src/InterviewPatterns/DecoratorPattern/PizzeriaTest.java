package InterviewPatterns.DecoratorPattern;

public class PizzeriaTest {
    public static void main(String[] args) {
        Pizza farmhousePizza = new Farmhouse();
        farmhousePizza = new CheeseDecorator(farmhousePizza);
        farmhousePizza = new JalapenoDecorator(farmhousePizza);
        farmhousePizza = new MushroomsDecorator(farmhousePizza);

        System.out.println("order : " + farmhousePizza.getDescription() + " , total cost : " + farmhousePizza.getCost());

        Pizza margheritaPizza = new Margherita();
        margheritaPizza = new MushroomsDecorator(margheritaPizza);
        margheritaPizza = new CheeseDecorator(margheritaPizza);

        System.out.println("order : " + margheritaPizza.getDescription() + ", total cost : " + margheritaPizza.getCost());

        Pizza margheritaPizza2 = new Margherita();
        margheritaPizza2 = new CheeseDecorator(margheritaPizza2);
        margheritaPizza2 = new OliveDecorator(margheritaPizza2);
        margheritaPizza2 = new MushroomsDecorator(margheritaPizza2);
        margheritaPizza2 = new JalapenoDecorator(margheritaPizza2);

        System.out.println("order : " + margheritaPizza2.getDescription() + " , total cost : " + margheritaPizza2.getCost());
    }
}
