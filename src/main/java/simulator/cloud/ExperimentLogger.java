package simulator.cloud;

import simulator.models.DrawableBody;
import simulator.models.Vector;
import javafx.scene.paint.Color;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.List;

public class ExperimentLogger {

    private static final String BACKEND_URL = "http://localhost:8080/experiments";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static void logSnapshot(
            String engineType,
            List<DrawableBody> bodies,
            double avgFPS,
            long computeTimeMs) {

        try {
            String json = buildSnapshotJson(engineType, bodies, avgFPS, computeTimeMs);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BACKEND_URL))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(response -> {
                        if (response.statusCode() == 201) {
                            System.out.println("Snapshot logged successfully");
                            return response.body();
                        } else {
                            System.err.println("Failed to log snapshot. Status: " + response.statusCode());
                            return null;
                        }
                    })
                    .exceptionally(e -> {
                        System.err.println("Error logging snapshot: " + e.getMessage());
                        return null;
                    });

        } catch (Exception e) {
            System.err.println("Error building snapshot: " + e.getMessage());
        }
    }

    private static String buildSnapshotJson(
            String engineType,
            List<DrawableBody> bodies,
            double avgFPS,
            long computeTimeMs) {

        StringBuilder json = new StringBuilder("{");
        json.append("\"timestamp\":\"").append(Instant.now().toString()).append("\",");
        json.append("\"engineType\":\"").append(engineType).append("\",");
        json.append("\"particleCount\":").append(bodies.size()).append(",");
        json.append("\"avgFPS\":").append(avgFPS).append(",");
        json.append("\"computeTimeMs\":").append(computeTimeMs).append(",");

        json.append("\"particles\":[");
        for (int i = 0; i < bodies.size(); i++) {
            DrawableBody body = bodies.get(i);
            Vector pos = body.getPosition();
            Vector vel = body.getVelocity();
            String hexColor = toHexColor(body.getFillColor());

            json.append("{");
            json.append("\"id\":").append(i).append(",");
            json.append("\"position\":[").append(pos.getValue(0)).append(",")
                                         .append(pos.getValue(1)).append("],");
            json.append("\"velocity\":[").append(vel.getValue(0)).append(",")
                                         .append(vel.getValue(1)).append("],");
            json.append("\"mass\":").append(body.getMass()).append(",");
            json.append("\"radius\":").append(body.getRadius()).append(",");
            json.append("\"color\":\"").append(hexColor).append("\"");
            json.append("}");

            if (i < bodies.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");

        json.append("}");
        return json.toString();
    }

    private static String toHexColor(Color color) {
        return String.format("#%02X%02X%02X",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255)
        );
    }
}
