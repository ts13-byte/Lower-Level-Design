package PracticeProblems.ShoppingCart;

import java.time.LocalDateTime;
import java.util.Set;

public class Main {

    private static int failures = 0;

    public static void main(String[] args) {
        LocalDateTime future = LocalDateTime.now().plusDays(7);
        LocalDateTime past = LocalDateTime.now().minusDays(1);

        Item laptop = new Item("I1", "Laptop", 1000, ItemCategory.ELECTRONICS);
        Item phone = new Item("I2", "Phone", 500, ItemCategory.ELECTRONICS);
        Item shirt = new Item("I3", "Shirt", 500, ItemCategory.CLOTHING);

        // 1. Ordering changes the answer: 10% then 100 off vs 100 off then 10%
        Cart a = new Cart("C1");
        a.addItem(laptop, 1);
        a.applyCoupon(cartCoupon("PCT10", false, 0, future, new PercentageStrategy(10)));
        a.applyCoupon(cartCoupon("FLAT100", false, 0, future, new FlatStrategy(100)));
        checkAmount("10% then 100 off", a.getTotal(), 800.0);

        Cart b = new Cart("C2");
        b.addItem(laptop, 1);
        b.applyCoupon(cartCoupon("FLAT100", false, 0, future, new FlatStrategy(100)));
        b.applyCoupon(cartCoupon("PCT10", false, 0, future, new PercentageStrategy(10)));
        checkAmount("100 off then 10%", b.getTotal(), 810.0);

        // 2. Item-level runs before cart-level: laptop 1000 -> 800 (20% electronics), shirt 500, sum 1300, flat 100 -> 1200
        Cart c = new Cart("C3");
        c.addItem(laptop, 1);
        c.addItem(shirt, 1);
        c.applyCoupon(itemCoupon("ELEC20", false, 0, future, Set.of(ItemCategory.ELECTRONICS), new PercentageStrategy(20)));
        c.applyCoupon(cartCoupon("FLAT100", false, 0, future, new FlatStrategy(100)));
        checkAmount("item coupon then cart coupon", c.getTotal(), 1200.0);

        // 3. Flat item-level coupon applies per matching line: (1000-50) + (500-50) + shirt 500 = 1900
        Cart d = new Cart("C4");
        d.addItem(laptop, 1);
        d.addItem(phone, 1);
        d.addItem(shirt, 1);
        d.applyCoupon(itemCoupon("ELEC50", false, 0, future, Set.of(ItemCategory.ELECTRONICS), new FlatStrategy(50)));
        checkAmount("flat item coupon per line", d.getTotal(), 1900.0);

        // 4. Exclusive coupon conflicts, in both directions
        Cart e1 = new Cart("C5");
        e1.addItem(laptop, 1);
        e1.applyCoupon(cartCoupon("EXCL", true, 0, future, new FlatStrategy(50)));
        checkReason("normal coupon after exclusive",
                e1.applyCoupon(cartCoupon("FLAT100", false, 0, future, new FlatStrategy(100))),
                RejectionReason.CONFLICTS_WITH_EXCLUSIVE);

        Cart e2 = new Cart("C6");
        e2.addItem(laptop, 1);
        e2.applyCoupon(cartCoupon("FLAT100", false, 0, future, new FlatStrategy(100)));
        checkReason("exclusive after normal coupon",
                e2.applyCoupon(cartCoupon("EXCL", true, 0, future, new FlatStrategy(50))),
                RejectionReason.CONFLICTS_WITH_EXCLUSIVE);

        Cart e3 = new Cart("C7");
        e3.addItem(laptop, 1);
        checkReason("exclusive coupon on its own",
                e3.applyCoupon(cartCoupon("EXCL", true, 0, future, new FlatStrategy(50))), null);
        checkAmount("exclusive coupon total", e3.getTotal(), 950.0);

        // 5. Minimum value re-checked at total time: laptop 1000 qualifies, then cart drops to 500
        Cart f = new Cart("C8");
        f.addItem(laptop, 1);
        checkReason("min-1000 coupon applies at 1000",
                f.applyCoupon(cartCoupon("MIN1000", false, 1000, future, new FlatStrategy(100))), null);
        checkAmount("total while qualifying", f.getTotal(), 900.0);
        f.removeItem("I1");
        f.addItem(shirt, 1);
        checkAmount("total after cart drops below min", f.getTotal(), 500.0);

        // 6. Rejection reasons
        Cart g = new Cart("C9");
        g.addItem(laptop, 1);
        checkReason("expired coupon",
                g.applyCoupon(cartCoupon("OLD", false, 0, past, new FlatStrategy(100))),
                RejectionReason.EXPIRED);

        Cart h = new Cart("C10");
        h.addItem(shirt, 1);
        checkReason("below minimum",
                h.applyCoupon(cartCoupon("MIN1000", false, 1000, future, new FlatStrategy(100))),
                RejectionReason.BELOW_MINIMUM);
        checkReason("category not in cart",
                h.applyCoupon(itemCoupon("ELEC20", false, 0, future, Set.of(ItemCategory.ELECTRONICS), new PercentageStrategy(20))),
                RejectionReason.NA_ON_CATEGORY);

        Cart i = new Cart("C11");
        i.addItem(laptop, 1);
        i.applyCoupon(cartCoupon("FLAT100", false, 0, future, new FlatStrategy(100)));
        checkReason("same code twice",
                i.applyCoupon(cartCoupon("FLAT100", false, 0, future, new FlatStrategy(100))),
                RejectionReason.ALREADY_APPLIED);

        // 7. Total never drops below zero
        Cart j = new Cart("C12");
        j.addItem(shirt, 1);
        j.applyCoupon(cartCoupon("BIG", false, 0, future, new FlatStrategy(1000)));
        checkAmount("discount larger than cart", j.getTotal(), 0.0);

        // 8. Item coupon goes quiet when its matching item is removed
        Cart k = new Cart("C13");
        k.addItem(laptop, 1);
        k.addItem(shirt, 1);
        k.applyCoupon(itemCoupon("ELEC20", false, 0, future, Set.of(ItemCategory.ELECTRONICS), new PercentageStrategy(20)));
        k.removeItem("I1");
        checkAmount("item coupon after matching item removed", k.getTotal(), 500.0);

        // 9. Adding the same item twice merges into one line
        Cart l = new Cart("C14");
        l.addItem(laptop, 1);
        l.addItem(laptop, 1);
        checkAmount("duplicate addItem merges", l.getSubtotal(), 2000.0);

        // 10. ITEM-scope coupon with no categories is rejected at construction
        try {
            itemCoupon("BAD", false, 0, future, Set.of(), new FlatStrategy(10));
            fail("empty-category ITEM coupon should have thrown");
        } catch (IllegalArgumentException ex) {
            pass("empty-category ITEM coupon throws");
        }

        System.out.println();
        System.out.println(failures == 0 ? "ALL CHECKS PASSED" : failures + " CHECK(S) FAILED");
    }

    // --- helpers to keep the tests readable ---

    private static Coupon cartCoupon(String code, boolean exclusive, double min,
                                     LocalDateTime expires, DiscountStrategy strategy) {
        return new Coupon(code, CouponScope.CART, exclusive, min, expires, Set.of(), strategy);
    }

    private static Coupon itemCoupon(String code, boolean exclusive, double min, LocalDateTime expires,
                                     Set<ItemCategory> categories, DiscountStrategy strategy) {
        return new Coupon(code, CouponScope.ITEM, exclusive, min, expires, categories, strategy);
    }

    // expected == null means "should have been applied"
    private static void checkReason(String name, ApplyResult result, RejectionReason expected) {
        boolean ok = (expected == null)
                ? result.isApplied()
                : (!result.isApplied() && result.getRejectionReason() == expected);
        if (ok) {
            pass(name + " -> " + result.getMessage());
        } else {
            fail(name + " (expected " + (expected == null ? "applied" : expected)
                    + ", got " + result.getMessage() + ")");
        }
    }

    private static void checkAmount(String name, double actual, double expected) {
        if (Math.abs(actual - expected) < 0.001) {
            pass(name + " = " + actual);
        } else {
            fail(name + " (expected " + expected + ", got " + actual + ")");
        }
    }

    private static void pass(String message) { System.out.println("PASS: " + message); }

    private static void fail(String message) { failures++; System.out.println("FAIL: " + message); }
}