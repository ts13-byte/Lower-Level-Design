package PracticeProblems.RateLimiter.model;

import java.util.Objects;

public class Client {
    private final String clientId;
    private final ClientConfiguration clientConfiguration;

    public Client(String clientId, ClientConfiguration clientConfiguration) {
        this.clientId = clientId;
        this.clientConfiguration = clientConfiguration;
    }

    public String getClientId() { return clientId; }
    public ClientConfiguration getClientConfiguration() { return clientConfiguration; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId);
    }
}
