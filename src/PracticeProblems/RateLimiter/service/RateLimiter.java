package PracticeProblems.RateLimiter.service;

import PracticeProblems.RateLimiter.strategy.RateLimitingStrategy;

public class RateLimiter {
    RateLimitingStrategy rateLimitingStrategy;


    public RateLimiter(RateLimitingStrategy rateLimitingStrategy) {
        this.rateLimitingStrategy = rateLimitingStrategy;
    }

    public boolean isAllowed(String clientId) {
        return rateLimitingStrategy.allowRequest(clientId);
    }
}
