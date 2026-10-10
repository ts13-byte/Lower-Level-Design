package PracticeProblems.ShoppingCart;

/**
 * Class models the result of a coupon application.
 */
public class ApplyResult {
    private final boolean applied;
    private final RejectionReason rejectionReason;

    public ApplyResult(boolean applied, RejectionReason rejectionReason) {
        this.applied = applied;
        this.rejectionReason = rejectionReason;
    }

    public boolean isApplied() {
        return applied;
    }

    public RejectionReason getRejectionReason() {
        return rejectionReason;
    }

    public static ApplyResult ok() {
        return new ApplyResult(true , null);
    }

    public static ApplyResult rejected(RejectionReason rejectionReason) {
        return new ApplyResult(false, rejectionReason);
    }

    public String getMessage() {
        return applied ? "Coupon applied" : rejectionReason.getMessage();
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
