package PracticeProblems.RateLimiter;

import PracticeProblems.RateLimiter.factory.ClientConfigurationFactory;
import PracticeProblems.RateLimiter.factory.TokenBucketFactory;
import PracticeProblems.RateLimiter.model.Client;
import PracticeProblems.RateLimiter.model.ClientTier;
import PracticeProblems.RateLimiter.service.RateLimiter;
import PracticeProblems.RateLimiter.strategy.TokenBucketStrategy;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        ClientConfigurationFactory configFactory = new ClientConfigurationFactory();
        TokenBucketFactory bucketFactory = new TokenBucketFactory();
        String freeClientId = UUID.randomUUID().toString();
        String premiumClientId = UUID.randomUUID().toString();
        String customClientId = UUID.randomUUID().toString();
        String unknownClientId = UUID.randomUUID().toString(); // never registered — tests the FREE fallback

        Client freeClient = new Client(freeClientId, configFactory.createTieredConfiguration(ClientTier.FREE));
        Client premiumClient = new Client(premiumClientId, configFactory.createTieredConfiguration(ClientTier.PREMIUM));
        Client customClient = new Client(customClientId, configFactory.createCustomConfiguration(20, 1.0));

        Map<String, Client> clients = new HashMap<>();
        clients.put(freeClientId, freeClient);
        clients.put(premiumClientId, premiumClient);
        clients.put(customClientId, customClient);

        TokenBucketStrategy strategy = new TokenBucketStrategy(bucketFactory, clients);
        RateLimiter rateLimiter = new RateLimiter(strategy);

        System.out.println("--- FREE client (" + freeClientId + ") ---");
        for (int i = 1; i <= 7; i++) {
            System.out.println("Request " + i + ": " + (rateLimiter.isAllowed(freeClientId) ? "ALLOWED" : "REJECTED"));
        }

        System.out.println("--- PREMIUM client (" + premiumClientId + ") ---");
        for (int i = 1; i <= 7; i++) {
            System.out.println("Request " + i + ": " + (rateLimiter.isAllowed(premiumClientId) ? "ALLOWED" : "REJECTED"));
        }

        System.out.println("--- CUSTOM client (" + customClientId + ") ---");
        int allowedCount = 0;
        for (int i = 1; i <= 22; i++) {
            if (rateLimiter.isAllowed(customClientId)) allowedCount++;
        }
        System.out.println("Allowed " + allowedCount + " out of 22 requests (expect 20)");

        System.out.println("--- Unregistered client (" + unknownClientId + ") ---");
        for (int i = 1; i <= 6; i++) {
            System.out.println("Request " + i + ": " + (rateLimiter.isAllowed(unknownClientId) ? "ALLOWED" : "REJECTED"));
        }

        System.out.println("--- FREE client: drain fully, wait, confirm partial refill ---");
        String refillTestClientId = UUID.randomUUID().toString();
        Client refillTestClient = new Client(refillTestClientId, configFactory.createTieredConfiguration(ClientTier.FREE));
        clients.put(refillTestClientId, refillTestClient);

    // Drain the bucket completely — capacity 5, so 5 requests should succeed
        for (int i = 1; i <= 5; i++) {
            boolean allowed = rateLimiter.isAllowed(refillTestClientId);
            System.out.println("Drain request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
        }

    // Immediately try a 6th — should be rejected, bucket is empty
        boolean immediateRetry = rateLimiter.isAllowed(refillTestClientId);
        System.out.println("Immediate 6th request: " + (immediateRetry ? "ALLOWED" : "REJECTED") + " (expect REJECTED)");

    // Wait 3 seconds — at 1 token/sec, expect ~3 tokens to have refilled
        Thread.sleep(3000);

    // Fire 4 requests: expect roughly 3 ALLOWED (refilled), then REJECTED once exhausted again
        int allowedAfterWait = 0;
        for (int i = 1; i <= 4; i++) {
            boolean allowed = rateLimiter.isAllowed(refillTestClientId);
            System.out.println("Post-wait request " + i + ": " + (allowed ? "ALLOWED" : "REJECTED"));
            if (allowed) allowedAfterWait++;
        }
        System.out.println("Allowed " + allowedAfterWait + " out of 4 post-wait requests (expect 3)");
    }
}
