package simulator;

import simulator.models.BodyBuilder;
import simulator.models.Vector;
import simulator.physics.PhysicsEngine;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.canvas.Canvas;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.canvas.GraphicsContext;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;


public class Main extends Application {

    private static final int FRAME_RATE = 60;
    private static final int canvasWidth = 800;
    private static final int canvasHeight = 600;
    private static final double worldWidth = 16.0;  // meters
    private static final double worldHeight = 12.0; // meters
    private static final double scale = canvasWidth / worldWidth; // pixels per meter

    private static final Color backgroundColor = Color.web("#1a1a1a");

    private PhysicsEngine engine;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        engine = new PhysicsEngine();
        engine.addBody(
                new BodyBuilder(2)
                        .setFillColor(Color.web("#2bfbe9"))
                        .setPosition(
                                new Vector(2)
                                        .setValue(0, 1)
                                        .setValue(1, 1)
                        )
                        .setVelocity(
                                new Vector(2)
                                        .setValue(0, 1)
                                        .setValue(0, 1)
                        )
                        .setRadius(10)
                        .setMass(1)
                        .buildDrawableBody()
        );

        BorderPane root = new BorderPane();
        canvas = new Canvas(canvasWidth, canvasHeight);
        graphicsContext = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        root.setBottom(createControlPanel());

        Scene scene = new Scene(root, canvasWidth, canvasHeight + 50);
        primaryStage.setTitle("Particle Simulator - Dyn4j + JavaFX");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        startGameLoop();

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
                engine.start();
                pauseBtn.setText("Pause");
            }
        });

        controls.getChildren().addAll(pauseBtn);
        return controls;
    }

    private void startGameLoop() {
        gameLoop = new AnimationTimer() {
            private static long nsPerSec = 1000000000;              // 1 s = 1000000000 ns
            private long prev = -1;                                  // previous timestamp
            private long dt = nsPerSec / (long) FRAME_RATE;         // change in time

            @Override
            public void handle(long now) {
                if(prev == -1) {
                    prev = now;
                    return;
                }
                if(now - prev < dt) {
                    return;
                }
                engine.update((double) (now - prev) / (double) nsPerSec);
                engine.draw(graphicsContext, backgroundColor, canvasWidth, canvasHeight, scale);
                prev = now;
            }
        };
        gameLoop.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}