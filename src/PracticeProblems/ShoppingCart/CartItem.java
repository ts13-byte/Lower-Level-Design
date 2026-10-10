package PracticeProblems.ShoppingCart;

public class CartItem {
    private final Item item;
    private int quantity;

    public CartItem(Item item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public Item getItem() {
        return item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Determines the original cart value before applying any kind of coupon.
     * @return primitive double - original cart value.
     */
    public double getLineTotal() {
        return item.getPrice() * quantity;
    }
}
