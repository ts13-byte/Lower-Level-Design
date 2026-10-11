package PracticeProblems.FoodDelivery;

public abstract class OrderState {

    public void confirm(Order order) {
        throw illegal("confirm");
    }

    public void startPreparing(Order order) {
        throw illegal("startPreparing");
    }

    public void dispatch(Order order) {
        throw illegal("dispatch");
    }

    public void deliver(Order order) {
        throw illegal("deliver");
    }

    public void cancel(Order order) {
        throw illegal("cancel");
    }


    private IllegalStateException illegal(String event) {
        return new IllegalStateException("Cannot " + event + " an order that is "
                + getClass().getSimpleName());
    }
}
