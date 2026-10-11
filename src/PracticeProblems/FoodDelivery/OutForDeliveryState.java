package PracticeProblems.FoodDelivery;

public class OutForDeliveryState extends OrderState {

    public void deliver(Order order) {
        order.setState(new OrderDeliveredState());
    }
}
