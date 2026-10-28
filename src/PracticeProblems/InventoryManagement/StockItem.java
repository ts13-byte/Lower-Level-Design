package PracticeProblems.InventoryManagement;

public class StockItem {
    private final Product product;
    private int onHand; // physically the amount of product in the warehouse
    private int reserved; // promised to the orders that have not been confirmed yet.

    public StockItem(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public synchronized int getOnHand() {
        return onHand;
    }

    public synchronized int getReserved() {
        return reserved;
    }

    /**
     * returns the available stock -> onHand - reserved , this is the amount that can be promised in the future.
     * @return
     */
    public synchronized int getAvailable() {
        return onHand - reserved;
    }

    /**
     * onHand += quantity
     * @param quantity
     */
    public synchronized void add(int quantity) {
        onHand += quantity;
    }

    /**
     * if requested quantity <= available(onHand - reserved) , to reserve the quantity i.e. reserved += quantity and return true , otherwise false.
     * no actual deduction of stock yet.
     * @param quantity
     * @return
     */
    public synchronized boolean tryReserve(int quantity) {
        if(quantity > onHand - reserved) return false;
        reserved += quantity;
        return true;
    }

    /**
     * onHand remains untouched , but the reserved -= quantity
     * @param quantity
     */
    public synchronized void release(int quantity) {
        reserved -= quantity;
    }

    /**
     * onHand -= quantity , reserved -= quantity -> actual deduction of stock.
     * @param quantity
     */
    public synchronized void confirm(int quantity) {
        onHand -= quantity;
        reserved -= quantity;
    }

}
