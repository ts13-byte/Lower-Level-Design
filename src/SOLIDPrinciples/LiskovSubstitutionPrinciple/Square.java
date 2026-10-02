package SOLIDPrinciples.LiskovSubstitutionPrinciple;

public class Square extends Shape{

    private int side;

    public void setSide(int side) {
        this.side = side;
    }

    @Override
    public int findArea() {
        return side * side;
    }
}
