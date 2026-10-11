package PracticeProblems.FoodDelivery;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * An order moving through its lifecycle: PLACED -> CONFIRMED -> PREPARING ->
 * OUT_FOR_DELIVERY -> DELIVERED, with CANCELLED reachable from PLACED and CONFIRMED.
 *
 * This class holds NO transition rules. It owns the current state object and hands every
 * event to it. All the "is this legal?" logic lives in the OrderState subclasses, which is
 * the whole point of the State pattern: adding or changing a state never touches Order.
 */
public class Order {
    private final String orderId;
    private OrderState state;

    // Notify extension (not wired in yet, see the stubs at the bottom)
    private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();

    /**
     * Every order starts in PLACED, so the caller doesn't choose a starting state.
     * Letting callers pass one in would allow an order to be created in an arbitrary state.
     */
    public Order(String orderId) {
        this.orderId = orderId;
        this.state = new PlacedState();
    }

    /**
     * Event: the restaurant accepts the order. Legal only from PLACED.
     *
     * Synchronized so the legality check (inside the state) and the state change happen as one
     * atomic step per order. Without it, two threads could both pass the check and both
     * write, e.g. an order that is both cancelled and being prepared. The lock is per Order,
     * so different orders never block each other.
     *
     * @throws IllegalStateException if the current state doesn't allow this event
     */
    public synchronized void confirm() { state.confirm(this); }

    /**
     * Event: the kitchen starts cooking. Legal only from CONFIRMED.
     * Synchronized for the same reason as confirm().
     *
     * @throws IllegalStateException if the current state doesn't allow this event
     */
    public synchronized void startPreparing() { state.startPreparing(this); }

    /**
     * Event: a delivery partner picks the order up. Legal only from PREPARING.
     * Synchronized for the same reason as confirm().
     *
     * @throws IllegalStateException if the current state doesn't allow this event
     */
    public synchronized void dispatch() { state.dispatch(this); }

    /**
     * Event: the order reaches the customer. Legal only from OUT_FOR_DELIVERY.
     * DELIVERED is terminal, so no further events are accepted afterwards.
     *
     * @throws IllegalStateException if the current state doesn't allow this event
     */
    public synchronized void deliver() { state.deliver(this); }

    /**
     * Event: the customer cancels. Legal only from PLACED and CONFIRMED; once the kitchen has
     * started, it's too late. This race against startPreparing() is the reason every event
     * method is synchronized: exactly one of the two wins, the other then sees the new state
     * and throws.
     *
     * @throws IllegalStateException if the current state doesn't allow this event
     */
    public synchronized void cancel() { state.cancel(this); }

    /**
     * Called only by the state classes to move the order to its next state. Package-private
     * on purpose: nothing outside the package can force a transition and bypass the legality
     * rules. It isn't synchronized itself, because it's only reached from the synchronized
     * event methods above, which already hold the lock. If you ever call it from anywhere
     * else, take the lock first.
     */
    void setState(OrderState next) {
        this.state = next;
        // notifyObservers(next);   // <- the single hook for notifications, enable once implemented
    }

    /**
     * Human-readable current status, derived from the state class name (e.g. "ConfirmedState"),
     * so no separate status enum has to be kept in sync with the state classes. Synchronized
     * so a reader on another thread always sees the latest state.
     */
    public synchronized String getStatus() { return state.getClass().getSimpleName(); }

    public String getOrderId() { return orderId; }

    // ---------------------------------------------------------------------------------
    // Notification extension: stubs only. Nothing calls them yet, so behavior is unchanged.
    // ---------------------------------------------------------------------------------

    /**
     * Registers a listener to be told about every state change (customer app, restaurant
     * dashboard, delivery partner app, ...). CopyOnWriteArrayList is used for the same reason
     * as in Cricbuzz: notifications iterate constantly, subscriptions are rare, and
     * subscribing mid-broadcast must not throw.
     */
    public void subscribe(OrderObserver observer) {
        throw new UnsupportedOperationException("TODO");   // observers.add(observer)
    }

    /** Removes a previously registered listener. */
    public void unsubscribe(OrderObserver observer) {
        throw new UnsupportedOperationException("TODO");   // observers.remove(observer)
    }

    /**
     * Tells every observer the order moved to a new state. Called from setState(), the one
     * place every transition passes through, so no individual state class needs to know
     * about notifications and a newly added state gets them for free.
     *
     * Trade-off to mention: this runs inside the order's lock, so a slow observer delays
     * further events on this order. In production you'd hand it to a queue or executor.
     */
    private void notifyObservers(OrderState newState) {
        throw new UnsupportedOperationException("TODO");   // loop and call onStateChanged
    }
}