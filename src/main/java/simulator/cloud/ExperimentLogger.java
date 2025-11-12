package simulator.cloud;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;

/**
 * Minimal HTTP client for logging experiments to the backend.
 * Synchronous, no callbacks, just sends data.
 */
public class ExperimentLogger {

    private static final String BACKEND_URL = "http://localhost:8080/experiments";
    private static final HttpClient client = HttpClient.newHttpClient();

    /**
     * Post experiment data to backend service
     */
    public static void logExperiment(
            String engineType,
            int particleCount,
            double avgFPS,
            long computeTimeMs) {

        try {
            // Build simple JSON manually
            String json = String.format(
                "{\"timestamp\":\"%s\",\"engineType\":\"%s\",\"particleCount\":%d,\"avgFPS\":%.2f,\"computeTimeMs\":%d}",
                Instant.now().toString(),
                engineType,
                particleCount,
                avgFPS,
                computeTimeMs
            );

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                System.out.println("✓ Experiment logged successfully");
            } else {
                System.err.println("✗ Failed to log experiment. Status: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("✗ Error logging experiment: " + e.getMessage());
        }
    }
}
