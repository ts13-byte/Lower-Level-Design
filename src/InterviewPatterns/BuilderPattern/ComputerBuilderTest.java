package InterviewPatterns.BuilderPattern;

public class ComputerBuilderTest {
    public static void main(String[] args) {
        Computer computer1 = new Computer.Builder("acer" , "7547579").ram("64").storage("256").build();
        System.out.println(computer1.toString());
    }
}
