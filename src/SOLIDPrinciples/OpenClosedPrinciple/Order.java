package SOLIDPrinciples.OpenClosedPrinciple;

import java.util.List;

public class Order {
        private String orderId;
        private List<String> items;
        private double totalAmount;

        public Order(String orderId, List<String> items, double totalAmount) {
            this.orderId = orderId;
            this.items = items;
            this.totalAmount = totalAmount;
        }

        public double getTotalAmount() { return totalAmount; }
        public String getOrderId() { return orderId; }
}

