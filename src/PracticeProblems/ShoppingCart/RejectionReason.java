package PracticeProblems.ShoppingCart;

public enum RejectionReason {
    EXPIRED("Coupon has expired"),
    BELOW_MINIMUM("Cart value is below the minimum required amount"),
    CONFLICTS_WITH_EXCLUSIVE("Coupon conflicts with an exclusive offer"),
    ALREADY_APPLIED("Coupon has already been applied"),
    NA_ON_CATEGORY("Coupon is not applicable to this category");

    private final String message;

    RejectionReason(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
