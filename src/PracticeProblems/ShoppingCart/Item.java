package PracticeProblems.ShoppingCart;

public class Item {
    private final String itemId;
    private final String name;
    private final double price;
    private final ItemCategory itemCategory;

    public Item(String itemId, String name, double price, ItemCategory itemCategory) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.itemCategory = itemCategory;
    }

    public String getItemId() {
        return itemId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public ItemCategory getCategory() {
        return itemCategory;
    }
}
