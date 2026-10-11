package PracticeProblems.FoodDelivery;

public class ConfirmedState extends OrderState {

    public void startPreparing(Order order) {
        order.setState(new PreparingState());
    }

    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

}
