package simulator.cloud;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Map;


public class ExperimentLogger {

    private static final String BACKEND_URL = "http://localhost:8080/experiments";
    private static final HttpClient client = HttpClient.newHttpClient();


    public static void logExperiment(
            String engineType,
            int particleCount,
            double avgFPS,
            long computeTimeMs,
            Map<String, Object> parameters,
            Map<String, Object> metrics) {

        try {
            String json = buildJson(engineType, particleCount, avgFPS, computeTimeMs, parameters, metrics);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                System.out.println("Experiment logged successfully: " + response.body());
            } else {
                System.err.println("Failed to log experiment. Status: " + response.statusCode());
            }

        } catch (Exception e) {
            System.err.println("Error logging experiment: " + e.getMessage());
        }
    }


    private static String buildJson(
            String engineType,
            int particleCount,
            double avgFPS,
            long computeTimeMs,
            Map<String, Object> parameters,
            Map<String, Object> metrics) {

        StringBuilder json = new StringBuilder("{");
        json.append("\"timestamp\":\"").append(Instant.now().toString()).append("\",");
        json.append("\"engineType\":\"").append(engineType).append("\",");
        json.append("\"particleCount\":").append(particleCount).append(",");
        json.append("\"avgFPS\":").append(avgFPS).append(",");
        json.append("\"computeTimeMs\":").append(computeTimeMs);

        if (parameters != null && !parameters.isEmpty()) {
            json.append(",\"parameters\":").append(mapToJson(parameters));
        }

        if (metrics != null && !metrics.isEmpty()) {
            json.append(",\"metrics\":").append(mapToJson(metrics));
        }

        json.append("}");
        return json.toString();
    }

    private static String mapToJson(Map<String, Object> map) {
        StringBuilder json = new StringBuilder("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            if (!first) json.append(",");
            json.append("\"").append(entry.getKey()).append("\":");
            Object value = entry.getValue();
            if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else {
                json.append(value);
            }
            first = false;
        }
        json.append("}");
        return json.toString();
    }
}
