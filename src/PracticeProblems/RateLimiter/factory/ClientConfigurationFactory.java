package PracticeProblems.RateLimiter.factory;


import PracticeProblems.RateLimiter.model.ClientConfiguration;
import PracticeProblems.RateLimiter.model.ClientTier;

public class ClientConfigurationFactory {
    public ClientConfiguration createTieredConfiguration(ClientTier tier) {
        if (tier == ClientTier.CUSTOM) {
            throw new IllegalArgumentException("CUSTOM tier requires explicit capacity and refill rate");
        }
        int capacity;
        double refillRate;
        switch (tier) {
            case FREE -> { capacity = 5; refillRate = 1.0; }
            case PREMIUM -> { capacity = 50; refillRate = 10.0; }
            default -> throw new IllegalArgumentException("Unknown tier: " + tier);
        }
        return new ClientConfiguration(tier, capacity, refillRate);
    }

    public ClientConfiguration createCustomConfiguration(int capacity, double refillRate) {
        if (capacity <= 0 || refillRate <= 0) {
            throw new IllegalArgumentException("Capacity and refill rate must be positive");
        }
        return new ClientConfiguration(ClientTier.CUSTOM, capacity, refillRate);
    }
}

