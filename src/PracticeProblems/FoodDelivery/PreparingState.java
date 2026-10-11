package PracticeProblems.FoodDelivery;

public class PreparingState extends OrderState {

    public void dispatch(Order order) {
        order.setState(new OutForDeliveryState());
    }
}
