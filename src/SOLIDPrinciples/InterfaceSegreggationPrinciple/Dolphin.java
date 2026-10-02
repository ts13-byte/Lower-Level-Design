package SOLIDPrinciples.InterfaceSegreggationPrinciple;

public class Dolphin implements Animal , Swimable{
    @Override
    public void eat() {
        System.out.println("dolphin is eating");
    }

    @Override
    public void breathe() {
        System.out.println("dolphin is breathing");
    }

    @Override
    public void swim() {
        System.out.println("dolphin is swimming");
    }
}
