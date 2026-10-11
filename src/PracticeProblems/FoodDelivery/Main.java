package PracticeProblems.FoodDelivery;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static int failures = 0;

    public static void main(String[] args) throws Exception {
        happyPath();
        legalCancels();
        illegalTransitions();
        raceCancelVsStartPreparing();
        raceManyConfirms();

        System.out.println();
        System.out.println(failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED");
    }

    // ---------- state machine ----------

    private static void happyPath() {
        Order o = new Order("O1");
        checkStatus("new order", o, "PlacedState");
        o.confirm();        checkStatus("after confirm", o, "ConfirmedState");
        o.startPreparing(); checkStatus("after startPreparing", o, "PreparingState");
        o.dispatch();       checkStatus("after dispatch", o, "OutForDeliveryState");
        o.deliver();        checkStatus("after deliver", o, "OrderDeliveredState");
    }

    private static void legalCancels() {
        Order fromPlaced = new Order("O2");
        fromPlaced.cancel();
        checkStatus("cancel from PLACED", fromPlaced, "CancelledState");

        Order fromConfirmed = new Order("O3");
        fromConfirmed.confirm();
        fromConfirmed.cancel();
        checkStatus("cancel from CONFIRMED", fromConfirmed, "CancelledState");
    }

    private static void illegalTransitions() {
        Order preparing = new Order("O4");
        preparing.confirm();
        preparing.startPreparing();
        checkThrows("cancel while PREPARING", preparing::cancel);
        checkStatus("state unchanged after illegal cancel", preparing, "PreparingState");

        checkThrows("dispatch a PLACED order (skips steps)", new Order("O5")::dispatch);

        Order delivered = new Order("O6");
        delivered.confirm(); delivered.startPreparing(); delivered.dispatch(); delivered.deliver();
        checkThrows("any event after DELIVERED", delivered::cancel);

        Order cancelled = new Order("O7");
        cancelled.cancel();
        checkThrows("any event after CANCELLED", cancelled::confirm);

        Order twice = new Order("O8");
        twice.confirm();
        checkThrows("confirm twice", twice::confirm);
    }

    // ---------- concurrency ----------

    /**
     * Customer cancels while the restaurant starts preparing, on a CONFIRMED order.
     * Exactly one must win. The loser must see the new state and throw.
     * Without synchronized, both can pass the check and both succeed.
     */
    private static void raceCancelVsStartPreparing() throws Exception {
        int rounds = 20000;
        int bothSucceeded = 0, bothFailed = 0, inconsistent = 0, cancelWins = 0, prepWins = 0;
        ExecutorService pool = Executors.newFixedThreadPool(2);

        for (int i = 0; i < rounds; i++) {
            Order order = new Order("RACE-" + i);
            order.confirm();

            CyclicBarrier gate = new CyclicBarrier(2);   // both threads fire at the same moment
            Callable<Boolean> cancelTask = () -> attempt(gate, order::cancel);
            Callable<Boolean> prepTask = () -> attempt(gate, order::startPreparing);

            Future<Boolean> cancelResult = pool.submit(cancelTask);
            Future<Boolean> prepResult = pool.submit(prepTask);
            boolean cancelOk = cancelResult.get();
            boolean prepOk = prepResult.get();

            if (cancelOk && prepOk) bothSucceeded++;
            else if (!cancelOk && !prepOk) bothFailed++;
            else if (cancelOk) {
                cancelWins++;
                if (!order.getStatus().equals("CancelledState")) inconsistent++;
            } else {
                prepWins++;
                if (!order.getStatus().equals("PreparingState")) inconsistent++;
            }
        }
        pool.shutdown();

        System.out.println("Race results over " + rounds + " rounds: cancel won " + cancelWins
                + ", startPreparing won " + prepWins);
        check("never both succeeded", bothSucceeded == 0, bothSucceeded + " rounds had both succeed");
        check("never both failed", bothFailed == 0, bothFailed + " rounds had both fail");
        check("final state always matches the winner", inconsistent == 0, inconsistent + " inconsistent rounds");
    }

    /** 100 threads all call confirm() on one PLACED order. Exactly one may succeed. */
    private static void raceManyConfirms() throws Exception {
        int threads = 100;
        Order order = new Order("MULTI");
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CyclicBarrier gate = new CyclicBarrier(threads);
        AtomicInteger successes = new AtomicInteger();
        AtomicInteger rejections = new AtomicInteger();
        CountDownLatch done = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                try {
                    gate.await();
                    order.confirm();
                    successes.incrementAndGet();
                } catch (IllegalStateException e) {
                    rejections.incrementAndGet();
                } catch (Exception e) {
                    Thread.currentThread().interrupt();
                } finally {
                    done.countDown();
                }
            });
        }
        done.await();
        pool.shutdown();

        check("exactly one confirm succeeded", successes.get() == 1, "successes = " + successes.get());
        check("the other 99 were rejected", rejections.get() == threads - 1, "rejections = " + rejections.get());
        checkStatus("final state after 100 confirms", order, "ConfirmedState");
    }

    // ---------- helpers ----------

    // waits at the gate, runs the action, returns true if it succeeded and false if it was rejected
    private static boolean attempt(CyclicBarrier gate, Runnable action) throws Exception {
        gate.await();
        try {
            action.run();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private static void checkStatus(String name, Order order, String expected) {
        check(name + " -> " + expected, order.getStatus().equals(expected),
                "expected " + expected + ", got " + order.getStatus());
    }

    private static void checkThrows(String name, Runnable action) {
        try {
            action.run();
            fail(name + " should have thrown");
        } catch (IllegalStateException e) {
            pass(name + " -> " + e.getMessage());
        }
    }

    private static void check(String name, boolean ok, String failureDetail) {
        if (ok) pass(name); else fail(name + " (" + failureDetail + ")");
    }

    private static void pass(String message) { System.out.println("PASS: " + message); }
    private static void fail(String message) { failures++; System.out.println("FAIL: " + message); }
}
