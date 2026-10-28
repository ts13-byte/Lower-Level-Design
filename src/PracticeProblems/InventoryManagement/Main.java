package PracticeProblems.InventoryManagement;


import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    private static int failures = 0;

    public static void main(String[] args) throws Exception {
        reserveConfirmRelease();
        doubleCompletion();
        multiLineRollback();
        raceManyReservers();
        raceConfirmVsRelease();

        System.out.println();
        System.out.println(failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED");
    }

    // ---------- basic flow ----------

    private static void reserveConfirmRelease() {
        Inventory inv = new Inventory();
        Product soap = new Product("P1", "Soap");
        Product shampoo = new Product("P2", "Shampoo");
        inv.addStock(soap, 10);
        inv.addStock(shampoo, 10);
        checkNumbers("soap after stocking", inv, soap, 10, 0, 10);

        Reservation r1 = inv.reserve(List.of(new OrderLine(soap, 6)));
        checkNumbers("soap after reserving 6", inv, soap, 10, 6, 4);

        inv.confirm(r1);
        checkNumbers("soap after confirm (stock has left)", inv, soap, 4, 0, 4);

        Reservation r2 = inv.reserve(List.of(new OrderLine(shampoo, 6)));
        inv.release(r2);
        checkNumbers("shampoo after reserve then release (nothing left)", inv, shampoo, 10, 0, 10);

        checkRejected("reserving more than available", () ->
                inv.reserve(List.of(new OrderLine(soap, 5))), InsufficientStockException.class);
        checkRejected("reserving a product that was never stocked", () ->
                inv.reserve(List.of(new OrderLine(new Product("P9", "Ghost"), 1))), InsufficientStockException.class);
    }

    // ---------- double confirm / release after confirm ----------

    private static void doubleCompletion() {
        Inventory inv = new Inventory();
        Product soap = new Product("P1", "Soap");
        inv.addStock(soap, 10);

        Reservation r = inv.reserve(List.of(new OrderLine(soap, 6)));
        inv.confirm(r);
        checkRejected("confirm twice", () -> inv.confirm(r), IllegalStateException.class);
        checkRejected("release after confirm", () -> inv.release(r), IllegalStateException.class);
        checkNumbers("stock deducted once, not twice", inv, soap, 4, 0, 4);
    }

    // ---------- all-or-nothing across lines ----------

    private static void multiLineRollback() {
        Inventory inv = new Inventory();
        Product soap = new Product("P1", "Soap");
        Product shampoo = new Product("P2", "Shampoo");
        inv.addStock(soap, 10);
        inv.addStock(shampoo, 2);

        // soap line succeeds first, shampoo line then fails: soap must be rolled back
        checkRejected("order where the second line is short", () ->
                inv.reserve(List.of(new OrderLine(soap, 5), new OrderLine(shampoo, 3))), InsufficientStockException.class);
        checkNumbers("soap fully rolled back after the failed order", inv, soap, 10, 0, 10);
        checkNumbers("shampoo untouched after the failed order", inv, shampoo, 2, 0, 2);

        Reservation ok = inv.reserve(List.of(new OrderLine(soap, 5), new OrderLine(shampoo, 2)));
        check("a valid multi-line order succeeds", ok.getOrderLines().size() == 2, "lines: " + ok.getOrderLines().size());
        checkNumbers("soap after valid multi-line order", inv, soap, 10, 5, 5);
        checkNumbers("shampoo after valid multi-line order", inv, shampoo, 2, 2, 0);
    }

    // ---------- concurrency ----------

    /** 100 threads each try to reserve 1 unit of a product with 10 in stock. Exactly 10 may win. */
    private static void raceManyReservers() throws Exception {
        int threads = 100;
        Inventory inv = new Inventory();
        Product soap = new Product("P1", "Soap");
        inv.addStock(soap, 10);

        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CyclicBarrier gate = new CyclicBarrier(threads);
        CountDownLatch done = new CountDownLatch(threads);
        AtomicInteger successes = new AtomicInteger();
        AtomicInteger rejections = new AtomicInteger();

        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                try {
                    gate.await();
                    inv.reserve(List.of(new OrderLine(soap, 1)));
                    successes.incrementAndGet();
                } catch (InsufficientStockException e) {
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

        check("exactly 10 of 100 reservations succeeded", successes.get() == 10, "successes = " + successes.get());
        check("the other 90 were rejected", rejections.get() == 90, "rejections = " + rejections.get());
        checkNumbers("final numbers (never oversold, never negative)", inv, soap, 10, 10, 0);
    }

    /** Confirm and release hit the same reservation at once. Exactly one may win. */
    private static void raceConfirmVsRelease() throws Exception {
        int rounds = 5000;
        int bothWon = 0, bothLost = 0, wrongNumbers = 0, confirmWon = 0, releaseWon = 0;
        ExecutorService pool = Executors.newFixedThreadPool(2);

        for (int i = 0; i < rounds; i++) {
            Inventory inv = new Inventory();
            Product soap = new Product("P1", "Soap");
            inv.addStock(soap, 10);
            Reservation r = inv.reserve(List.of(new OrderLine(soap, 4)));

            CyclicBarrier gate = new CyclicBarrier(2);
            Future<Boolean> c = pool.submit(() -> attempt(gate, () -> inv.confirm(r)));
            Future<Boolean> l = pool.submit(() -> attempt(gate, () -> inv.release(r)));
            boolean confirmOk = c.get();
            boolean releaseOk = l.get();

            if (confirmOk && releaseOk) bothWon++;
            else if (!confirmOk && !releaseOk) bothLost++;
            else if (confirmOk) {
                confirmWon++;
                // stock left the building: onHand 6, reserved 0
                if (inv.getOnHand(soap) != 6 || inv.getReserved(soap) != 0) wrongNumbers++;
            } else {
                releaseWon++;
                // stock came back: onHand 10, reserved 0
                if (inv.getOnHand(soap) != 10 || inv.getReserved(soap) != 0) wrongNumbers++;
            }
        }
        pool.shutdown();

        System.out.println("Race over " + rounds + " rounds: confirm won " + confirmWon + ", release won " + releaseWon);
        check("never both confirm and release won", bothWon == 0, bothWon + " rounds");
        check("never both lost", bothLost == 0, bothLost + " rounds");
        check("final numbers always match the winner", wrongNumbers == 0, wrongNumbers + " rounds");
    }

    // ---------- helpers ----------

    private static boolean attempt(CyclicBarrier gate, Runnable action) throws Exception {
        gate.await();
        try {
            action.run();
            return true;
        } catch (IllegalStateException e) {
            return false;
        }
    }

    private static void checkNumbers(String name, Inventory inv, Product p, int onHand, int reserved, int available) {
        boolean ok = inv.getOnHand(p) == onHand && inv.getReserved(p) == reserved && inv.getAvailable(p) == available;
        check(name + " (onHand " + onHand + ", reserved " + reserved + ", available " + available + ")", ok,
                "got onHand " + inv.getOnHand(p) + ", reserved " + inv.getReserved(p) + ", available " + inv.getAvailable(p));
    }

    private static void checkRejected(String name, Runnable action, Class<? extends RuntimeException> expected) {
        try {
            action.run();
            fail(name + " should have thrown " + expected.getSimpleName());
        } catch (RuntimeException e) {
            if (expected.isInstance(e)) pass(name + " -> " + e.getMessage());
            else fail(name + " threw " + e.getClass().getSimpleName() + " instead of " + expected.getSimpleName());
        }
    }

    private static void check(String name, boolean ok, String failureDetail) {
        if (ok) pass(name); else fail(name + " (" + failureDetail + ")");
    }

    private static void pass(String message) { System.out.println("PASS: " + message); }
    private static void fail(String message) { failures++; System.out.println("FAIL: " + message); }
}
