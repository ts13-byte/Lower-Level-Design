package SOLIDPrinciples.LiskovSubstitutionPrinciple;

public class ShapeTest {
    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle();
        rectangle.setHeight(10);
        rectangle.setWidth(5);
        System.out.println(rectangle.findArea());

        Square square = new Square();
        square.setSide(2);
        System.out.println(square.findArea());
    }
}
