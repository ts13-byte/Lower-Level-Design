package PracticeProblems.InventoryManagement;

import InterviewPatterns.ObserverPattern.Stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Inventory {
    private final Map<Product , StockItem> stock = new ConcurrentHashMap<>();

    /**
     * adds new stock for a product and puts it inside the stock map.
     * @param product
     * @param quantity
     */
    public void addStock(Product product , int quantity) {
        //-> calls the constructor new StockItem(product) -> calls the add(quantity) on the returned stockItem object from constructor.
        stock.computeIfAbsent(product , StockItem::new).add(quantity);
    }

    /**
     * For a given product inside the stock map , finds out the available quantity for that product
     * if stockItem is null (not present inside the map) -> return 0.
     * @param product
     * @return
     */
    public int getAvailable(Product product) {
        StockItem stockItem = stock.get(product);
        return stockItem == null ? 0 : stockItem.getAvailable();
    }

    /**
     * Reserve the stock for order lines -> Reservation status = Active.
     * it is all or nothing.
     * @param orderLines
     * @return
     */
    public Reservation reserve(List<OrderLine> orderLines) {
        List<OrderLine> reservedSoFar = new ArrayList<>();
        for(OrderLine orderLine : orderLines) {
            StockItem item = stock.get(orderLine.getProduct());
            if(item == null || !item.tryReserve(orderLine.getQuantity())) {
                rollback(reservedSoFar);
                throw new InsufficientStockException("Insufficient stock for " + orderLine.getProduct().getProductName());
            }
            reservedSoFar.add(orderLine);
        }

        return new Reservation(UUID.randomUUID().toString(), reservedSoFar);
    }


    public void confirm(Reservation reservation) {
        if (!reservation.tryComplete(ReservationStatus.CONFIRMED)) {
            throw new IllegalStateException("Reservation " + reservation.getReservationId()
                    + " is already " + reservation.getStatus());
        }
        for (OrderLine line : reservation.getOrderLines()) {
            stock.get(line.getProduct()).confirm(line.getQuantity());
        }
    }

    public void release(Reservation reservation) {
        if (!reservation.tryComplete(ReservationStatus.RELEASED)) {
            throw new IllegalStateException("Reservation " + reservation.getReservationId()
                    + " is already " + reservation.getStatus());
        }
        for (OrderLine line : reservation.getOrderLines()) {
            stock.get(line.getProduct()).release(line.getQuantity());
        }
    }

    private void rollback(List<OrderLine> reservedSoFar) {
        for (OrderLine line : reservedSoFar) {
            stock.get(line.getProduct()).release(line.getQuantity());
        }
    }


    public int getOnHand(Product product) {
        StockItem item = stock.get(product);
        return item == null ? 0 : item.getOnHand();
    }

    public int getReserved(Product product) {
        StockItem item = stock.get(product);
        return item == null ? 0 : item.getReserved();
    }
}
