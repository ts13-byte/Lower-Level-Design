package PracticeProblems.FoodDelivery;

public interface OrderObserver {
    void onStateChanged(String orderId, String newStatus);
}
