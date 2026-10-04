package PracticeProblems.RateLimiter.strategy;

import PracticeProblems.RateLimiter.factory.ClientConfigurationFactory;
import PracticeProblems.RateLimiter.factory.TokenBucketFactory;
import PracticeProblems.RateLimiter.model.Client;
import PracticeProblems.RateLimiter.model.ClientConfiguration;
import PracticeProblems.RateLimiter.model.ClientTier;
import PracticeProblems.RateLimiter.model.TokenBucket;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TokenBucketStrategy implements RateLimitingStrategy {
    private Map<String , TokenBucket> clientBuckets; // To manage a token bucket per client
    private TokenBucketFactory tokenBucketFactory;
    private Map<String, Client> clients; // To keep track of which clientId belongs to which clientTier.

    public TokenBucketStrategy (TokenBucketFactory tokenBucketFactory, Map<String, Client> clients) {
        this.clientBuckets = new ConcurrentHashMap<>(); // no two same clientId request creation request make two separate buckets.
        this.tokenBucketFactory = tokenBucketFactory;
        this.clients = clients;
    }

    /**
     * For an incoming request for a clientId , assigns a token bucket if not already present according to the client tiers and
     * calls the tryConsume() synchronized method for that request.
     * rejects a request if enough tokens are not present.
     * @param clientId
     * @return true/false
     */
    @Override
    public boolean allowRequest(String clientId) {
        TokenBucket bucket = clientBuckets.computeIfAbsent(clientId, id -> {
            Client client = clients.getOrDefault(id, defaultFreeClient(id));
            return tokenBucketFactory.createTokenBucket(client.getClientConfiguration());
        });
        return bucket.tryConsume();
    }

    private Client defaultFreeClient(String clientId) {
        ClientConfiguration freeConfig = new ClientConfigurationFactory().createTieredConfiguration(ClientTier.FREE);
        return new Client(clientId, freeConfig);
    }
}
