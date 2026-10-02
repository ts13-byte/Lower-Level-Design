package SOLIDPrinciples.InterfaceSegreggationPrinciple;

public class Dog implements Animal , Runnable {

    @Override
    public void eat() {
        System.out.println("dog is eating");
    }

    @Override
    public void breathe() {
        System.out.println("dog is breathing");
    }

    @Override
    public void run() {
        System.out.println("dog is running");
    }
}
