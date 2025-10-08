package simulator;

import simulator.physics.PhysicsEngine;
import simulator.models.Particle;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

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
        createBoundaryWalls();

        javafx.scene.layout.BorderPane root = new javafx.scene.layout.BorderPane();
        canvas = new javafx.scene.canvas.Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        javafx.scene.layout.HBox controls = createControlPanel();
        root.setBottom(controls);

        javafx.scene.Scene scene = new javafx.scene.Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT + 50);
        primaryStage.setTitle("Particle Simulator - Dyn4j + JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        spawnRandomParticles(engine, 50, WORLD_WIDTH, WORLD_HEIGHT);
        startGameLoop();
        engine.start();

        System.out.println("Particle Simulator started with 50 particles!");
    }
    private void createBoundaryWalls() {
        createBoundaryWall(WORLD_WIDTH, 0.5, WORLD_WIDTH / 2, -0.25);
        createBoundaryWall(WORLD_WIDTH, 0.5, WORLD_WIDTH / 2, WORLD_HEIGHT + 0.25);
        createBoundaryWall(0.5, WORLD_HEIGHT, -0.25, WORLD_HEIGHT / 2);
        createBoundaryWall(0.5, WORLD_HEIGHT, WORLD_WIDTH + 0.25, WORLD_HEIGHT / 2);
    }

    private void createBoundaryWall(double width, double height, double x, double y) {
        org.dyn4j.dynamics.Body boundary = new org.dyn4j.dynamics.Body();
        boundary.addFixture(new org.dyn4j.geometry.Rectangle(width, height));
        boundary.setMass(org.dyn4j.geometry.MassType.INFINITE);
        boundary.translate(x, y);
        engine.getWorld().addBody(boundary);
    }

    private javafx.scene.layout.HBox createControlPanel() {
        javafx.scene.layout.HBox controls = new javafx.scene.layout.HBox(10);
        controls.setPadding(new javafx.geometry.Insets(10));
        controls.setAlignment(javafx.geometry.Pos.CENTER);
        controls.setStyle("-fx-background-color: #2c3e50;");

        javafx.scene.control.Button pauseBtn = new javafx.scene.control.Button("Pause");
        pauseBtn.setOnAction(e -> {
            if (engine.isRunning()) {
                engine.pause();
                pauseBtn.setText("Resume");
            } else {
                engine.resume();
                pauseBtn.setText("Pause");
            }
        });

        javafx.scene.control.Button resetBtn = new javafx.scene.control.Button("Reset");
        resetBtn.setOnAction(e -> {
            engine.reset();
            spawnRandomParticles(engine, 50, WORLD_WIDTH, WORLD_HEIGHT);
            engine.start();
        });

        javafx.scene.control.Button addParticlesBtn = new javafx.scene.control.Button("Add 10 Particles");
        addParticlesBtn.setOnAction(e -> {
            spawnRandomParticles(engine, 10, WORLD_WIDTH, WORLD_HEIGHT);
        });

        controls.getChildren().addAll(pauseBtn, resetBtn, addParticlesBtn);
        return controls;
    }

    private void startGameLoop() {
        gameLoop = new javafx.animation.AnimationTimer() {
            @Override
            public void handle(long now) {
                engine.update();
                render();
            }
        };
        gameLoop.start();
    }

    private void render() {
        gc.setFill(javafx.scene.paint.Color.web("#1a1a1a"));
        gc.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

        for (Particle particle : engine.getParticles()) {
            double screenX = particle.getX() * SCALE;
            double screenY = CANVAS_HEIGHT - (particle.getY() * SCALE);
            double screenRadius = particle.getRadius() * SCALE;

            gc.setFill(javafx.scene.paint.Color.web(particle.getColor()));
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

    private void spawnRandomParticles(PhysicsEngine engine, int count,
                                      double canvasWidth, double canvasHeight) {
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            double x = rand.nextDouble() * (canvasWidth - 2) + 1;
            double y = rand.nextDouble() * (canvasHeight - 2) + 1;
            double radius = 0.2 + rand.nextDouble() * 0.3;
            double mass = 1 + rand.nextDouble() * 9;

            Particle particle = new Particle(x, y, radius, mass);

            double vx = (rand.nextDouble() - 0.5) * 10;
            double vy = (rand.nextDouble() - 0.5) * 10;
            particle.setVelocity(vx, vy);

            String[] colors = {"#FF6B6B", "#4ECDC4", "#45B7D1", "#FFA07A", "#98D8C8"};
            particle.setColor(colors[rand.nextInt(colors.length)]);

            engine.addParticle(particle);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
