package PracticeProblems.ShoppingCart;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Coupon {
    private final String code;
    private final CouponScope couponScope;
    private final boolean isExclusive;
    private final double minCartValue;
    private final LocalDateTime expiresAt;
    private final Set<ItemCategory> targetCategories;
    private final DiscountStrategy discountStrategy;

    public Coupon(String code, CouponScope couponScope, boolean isExclusive, double minCartValue,
                  LocalDateTime expiresAt, Set<ItemCategory> targetCategories, DiscountStrategy discountStrategy) {
        Set<ItemCategory> categories = (targetCategories == null) ? Set.of() : targetCategories;
        if (couponScope == CouponScope.ITEM && categories.isEmpty()) {
            throw new IllegalArgumentException("An ITEM-scope coupon needs at least one target category");
        }
        this.code = code;
        this.couponScope = couponScope;
        this.isExclusive = isExclusive;
        this.minCartValue = minCartValue;
        this.expiresAt = expiresAt;
        this.targetCategories = categories;
        this.discountStrategy = discountStrategy;
    }

    public String getCode() {
        return code;
    }

    public CouponScope getCouponScope() {
        return couponScope;
    }

    public boolean isExclusive() {
        return isExclusive;
    }

    public double getMinCartValue() {
        return minCartValue;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public Set<ItemCategory> getTargetCategories() {
        return targetCategories;
    }

    public DiscountStrategy getDiscountStrategy() {
        return discountStrategy;
    }

    /**
     * Checks if a specific coupon can be applied to a cartItem based on the cartItem category.
     * @param cartItem
     * @return
     */
    public boolean appliesTo(CartItem cartItem) {
        return targetCategories.contains(cartItem.getItem().getCategory());
    }

    /**
     * Checks whether the coupon is applicable at a given time or not.
     * @param now
     * @return
     */
    public boolean isExpired(LocalDateTime now) {
        return now.isAfter(expiresAt);
    }

    /**
     * checks if the coupon meets the minimum cart value of the coupon.
     * @param subtotal
     * @return
     */
    public boolean meetsMinimum(double subtotal) {
        return subtotal >= minCartValue;
    }

    /**
     * checks if the coupon is still active or not at a given time and also if the lineTotal is above the minCartValue of the coupon.
     * @param subtotal
     * @param now
     * @return
     */
    public boolean isActive(double subtotal, LocalDateTime now) {
        return !isExpired(now) && meetsMinimum(subtotal);
    }

    /**
     * checks if an exclusive coupon conflicts with any other coupon, whichever one is exclusive
     * @param other
     * @return
     */
    public boolean conflictsWith(Coupon other) {
        return this.isExclusive || other.isExclusive;
    }

    /**
     * Checks whether the coupon is applicable or not according to different rules.
     * @param subtotal
     * @param applied
     * @param now
     * @return
     */
    public Optional<RejectionReason> checkCanApply(double subtotal, Collection<CartItem> items,
                                                   List<Coupon> applied, LocalDateTime now) {
        if (isExpired(now)) {
            return Optional.of(RejectionReason.EXPIRED);
        }
        for (Coupon other : applied) {
            if (other.getCode().equals(code)) {
                return Optional.of(RejectionReason.ALREADY_APPLIED);
            }
        }
        if (couponScope == CouponScope.ITEM && items.stream().noneMatch(this::appliesTo)) {
            return Optional.of(RejectionReason.NA_ON_CATEGORY);
        }
        if (!meetsMinimum(subtotal)) {
            return Optional.of(RejectionReason.BELOW_MINIMUM);
        }
        for (Coupon other : applied) {
            if (conflictsWith(other)) {
                return Optional.of(RejectionReason.CONFLICTS_WITH_EXCLUSIVE);
            }
        }
        return Optional.empty();
    }

}
