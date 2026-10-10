package PracticeProblems.ShoppingCart;

import java.time.LocalDateTime;
import java.util.*;

public class Cart {
    private final String cartId;
    private final Map<String, CartItem> items = new LinkedHashMap<>(); // itemId -> line
    private final List<Coupon> appliedCoupons = new ArrayList<>();     // in applied order

    public Cart(String cartId) {
        this.cartId = cartId;
    }


    /**
     * Adds a new item in the cart , models it into a CartItem object.
     * @param item
     * @param quantity
     */
    public void addItem(Item item, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        CartItem line = items.get(item.getItemId());
        if (line == null) {
            items.put(item.getItemId(), new CartItem(item, quantity));
            System.out.println("Added " + quantity + " x " + item.getName());
        } else {
            line.setQuantity(line.getQuantity() + quantity);
            System.out.println("Added " + quantity + " more x " + item.getName()
                    + " (now " + line.getQuantity() + ")");
        }
    }

    /**
     * Removes an item from the cart which is present in the cartItemList.
     * @param itemId
     */
    public boolean removeItem(String itemId) {
        CartItem removed = items.remove(itemId);
        if (removed == null) {
            System.out.println("Item " + itemId + " is not in the cart");
            return false;
        }
        System.out.println("Removed " + removed.getItem().getName());
        return true;
    }

    /**
     *  Returns the line total , without any coupon discount mentioned.
     * @return
     */
    public double getSubtotal() {
        double sum = 0;
        for (CartItem line : items.values()) {
            sum += line.getLineTotal();
        }
        return sum;
    }

    /**
     *  checks in order: NA_ON_CATEGORY,EXPIRED, ALREADY_APPLIED, BELOW_MINIMUM, CONFLICTS_WITH_EXCLUSIVE; otherwise add and return ok()
     * @return
     */
    public ApplyResult applyCoupon(Coupon coupon) {
        Optional<RejectionReason> rejection =
                coupon.checkCanApply(getSubtotal(), items.values(), appliedCoupons, LocalDateTime.now());
        if (rejection.isPresent()) {
            return ApplyResult.rejected(rejection.get());
        }
        appliedCoupons.add(coupon);
        return ApplyResult.ok();
    }

    /**
     * returns the total price after applying coupons in order.
     * @return
     */
    public double getTotal() {
        LocalDateTime now = LocalDateTime.now();
        double subtotal = getSubtotal(); // computed once, not per coupon

        double total = 0;
        for (CartItem line : items.values()) {
            total += priceAfterItemCoupons(line, subtotal, now);
        }
        for (Coupon coupon : appliedCoupons) {
            if (coupon.getCouponScope() == CouponScope.CART && coupon.isActive(subtotal, now)) {
                total = coupon.getDiscountStrategy().apply(total);
            }
        }
        return Math.max(0, total);
    }


    // helper: starts from the line total, applies every eligible ITEM coupon that matches this item, in applied order
    private double priceAfterItemCoupons(CartItem cartItem, double subtotal, LocalDateTime now) {
        double price = cartItem.getLineTotal();
        for (Coupon coupon : appliedCoupons) {
            if (coupon.getCouponScope() == CouponScope.ITEM
                    && coupon.appliesTo(cartItem)
                    && coupon.isActive(subtotal, now)) {
                price = coupon.getDiscountStrategy().apply(price);
            }
        }
        return price;
    }
}
