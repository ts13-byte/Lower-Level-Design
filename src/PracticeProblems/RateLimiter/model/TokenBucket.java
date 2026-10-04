package PracticeProblems.RateLimiter.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class TokenBucket {
    private int capacity;
    private double refillRatePerSecond;
    private double currentTokens; // taken as double suppose 0.5 tokens per sec rate
    private LocalDateTime lastRefillTime;


    public TokenBucket(int capacity, double refillRatePerSecond) {
        this.capacity = capacity;
        this.refillRatePerSecond = refillRatePerSecond;
        this.currentTokens = capacity; // starting full.
        this.lastRefillTime = LocalDateTime.now(); // start with the time of creation of Token bucket.
    }

    /**
     * method through which a client tries to consume a token , if it succeeds then return true and subtract an available
     * token else reject the request if not sufficient tokens , for two threads simultaneously trying to consume
     * synchronized keyword.
      * @return true/false
     */
    public synchronized boolean tryConsume() {
        // CASE : at the time of consumption first check if any tokens were added in the lapsed time.
        refill();
        if(currentTokens >= 1) {
            currentTokens -= 1;
            return true;
        }

        return false;
    }

    /**
     * this method finds out how many tokens has been added between the time of the request and since the last refill time.
     * deals in seconds.
     */
    private void refill() {
        LocalDateTime now = LocalDateTime.now();
        // CASE : find the precise time that has elapsed down to ms then convert back to seconds.
        double elapsedTime = Duration.between(lastRefillTime, now).toMillis() / 1000.0;
        double tokensRefilled = elapsedTime * refillRatePerSecond;
        // CASE : tokens cannot exceed more than the capacity of the token bucket
        currentTokens = Math.min(capacity , currentTokens + tokensRefilled);
        lastRefillTime = now;
    }
}
