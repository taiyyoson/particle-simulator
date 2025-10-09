package view;

import simulator.physics.PhysicsEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class Main extends Application {

    private static final int FRAME_RATE = 60;
    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 600;
    private static final double WORLD_WIDTH = 16.0;  // meters
    private static final double WORLD_HEIGHT = 12.0; // meters
    private static final double SCALE = CANVAS_WIDTH / WORLD_WIDTH; // pixels per meter

    private Window window;
    private PhysicsEngine engine;
    private Canvas canvas;
    private AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        engine = PhysicsEngine.builder().build();

        BorderPane root = new BorderPane();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        root.setCenter(canvas);
        window = Window.builder(
                engine,
                canvas.getGraphicsContext2D(),
                CANVAS_WIDTH / WORLD_WIDTH,
                CANVAS_WIDTH, CANVAS_HEIGHT
        ).build();

        root.setBottom(createControlPanel());

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

    private HBox createControlPanel() {
        HBox controls = new HBox(10);
        controls.setPadding(new Insets(10));
        controls.setAlignment(Pos.CENTER);
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
                engine.update((double) (now - prev) / (double) nsPerSec);
                render();
                prev = now;
            }
        };
        gameLoop.start();
    }

    private void render() {
        window.render();
    }

    public static void main(String[] args) {
        launch(args);
    }
}