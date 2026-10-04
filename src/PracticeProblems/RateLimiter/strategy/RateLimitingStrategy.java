package PracticeProblems.RateLimiter.strategy;

public interface RateLimitingStrategy {

    boolean allowRequest(String clientId);
}
