package PracticeProblems.FoodDelivery;

public class PlacedState extends OrderState {

    @Override
    public void confirm(Order order) {
        order.setState(new ConfirmedState());
    }

    @Override
    public void cancel(Order order) {
        order.setState(new CancelledState());
    }

}
