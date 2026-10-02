package SOLIDPrinciples.InterfaceSegreggationPrinciple;

public class Eagle  implements Animal , Flyable{

    @Override
    public void eat() {
        System.out.println("Eagle is eating");
    }

    @Override
    public void breathe() {
        System.out.println("eagle is breathing");
    }

    @Override
    public void fly() {
        System.out.println("eagle is flying");
    }
}
