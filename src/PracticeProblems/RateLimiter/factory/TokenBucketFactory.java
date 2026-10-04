package PracticeProblems.RateLimiter.factory;

import PracticeProblems.RateLimiter.model.ClientConfiguration;
import PracticeProblems.RateLimiter.model.TokenBucket;

public class TokenBucketFactory {
    public TokenBucket createTokenBucket(ClientConfiguration config) {
        return new TokenBucket(config.getCapacity(), config.getRefillRatePerSecond());
    }
}
