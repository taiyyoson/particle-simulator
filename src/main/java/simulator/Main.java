package simulator;

import simulator.physics.PhysicsEngine;
import simulator.models.Particle;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

import java.util.Random;

public class Main extends Application {

    private static final int FRAME_RATE = 60;
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final double WORLD_WIDTH = 16.0;  // meters
    private static final double WORLD_HEIGHT = 12.0; // meters
    private static final double SCALE = CANVAS_WIDTH / WORLD_WIDTH; // pixels per meter

    private PhysicsEngine engine;
    private javafx.scene.canvas.Canvas canvas;
    private javafx.scene.canvas.GraphicsContext gc;
    private javafx.animation.AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        engine = PhysicsEngine.builder().build();

        BorderPane root = new BorderPane();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        HBox controls = createControlPanel();
        root.setBottom(controls);

        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT + 50);
        primaryStage.setTitle("Particle Simulator - Dyn4j + JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        engine.addRandomParticles(50);
        startGameLoop();
        engine.start();

        System.out.println("Particle Simulator started with 50 particles!");
    }

    private javafx.scene.layout.HBox createControlPanel() {
        HBox controls = new HBox(10);
        controls.setPadding(new javafx.geometry.Insets(10));
        controls.setAlignment(javafx.geometry.Pos.CENTER);
        controls.setStyle("-fx-background-color: #2c3e50;");

        Button pauseBtn = new Button("Pause");
        pauseBtn.setOnAction(e -> {
            if (engine.isRunning()) {
                engine.pause();
                pauseBtn.setText("Resume");
            } else {
                engine.resume();
                pauseBtn.setText("Pause");
            }
        });

        Button resetBtn = new Button("Reset");
        resetBtn.setOnAction(e -> {
            engine.reset();
            engine.addRandomParticles(50);
            engine.start();
        });

        Button addParticlesBtn = new Button("Add 10 Particles");
        addParticlesBtn.setOnAction(e -> {
            engine.addRandomParticles(10);
        });

        controls.getChildren().addAll(pauseBtn, resetBtn, addParticlesBtn);
        return controls;
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            private static long nsPerSec = 1000000000;
            private long prev = 0;
            private long dt = nsPerSec / (long) FRAME_RATE;
            @Override
            public void handle(long now) {
                if(now - prev < dt) {
                    return;
                }
                engine.update((now - prev) / nsPerSec);
                render();
                prev = now;
            }
        };
        gameLoop.start();
    }

    private void render() {
        gc.setFill(Color.web("#1a1a1a"));
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        for (Particle particle : engine.getParticles()) {
            double screenX = particle.getX() * SCALE;
            double screenY = CANVAS_HEIGHT - (particle.getY() * SCALE);
            double screenRadius = particle.getRadius() * SCALE;

            gc.setFill(Color.web(particle.getColor()));
            gc.fillOval(screenX - screenRadius, screenY - screenRadius,
                       screenRadius * 2, screenRadius * 2);

            gc.setStroke(javafx.scene.paint.Color.WHITE);
            gc.setLineWidth(1);
            gc.strokeOval(screenX - screenRadius, screenY - screenRadius,
                         screenRadius * 2, screenRadius * 2);
        }

        gc.setFill(javafx.scene.paint.Color.WHITE);
        gc.setFont(javafx.scene.text.Font.font("Monospace", 14));
        gc.fillText("Particles: " + engine.getParticles().size(), 10, 20);
    }

    public static void main(String[] args) {
        launch(args);
    }
}