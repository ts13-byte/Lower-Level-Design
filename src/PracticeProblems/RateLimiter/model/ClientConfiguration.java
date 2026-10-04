package PracticeProblems.RateLimiter.model;

public class ClientConfiguration {
    private final ClientTier clientTier;
    private final int capacity;
    private final double refillRatePerSecond;

    public ClientConfiguration(ClientTier clientTier, int capacity, double refillRatePerSecond) {
        if (capacity <= 0 || refillRatePerSecond <= 0) {
            throw new IllegalArgumentException("Capacity and refill rate must be positive");
        }
        this.clientTier = clientTier;
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
    }

    public ClientTier getClientTier() { return clientTier; }
    public int getCapacity() { return capacity; }
    public double getRefillRatePerSecond() { return refillRatePerSecond; }
}
