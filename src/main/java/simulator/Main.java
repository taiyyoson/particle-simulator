package simulator;

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
import javafx.scene.text.Font;


public class Main extends Application {

    private static final int FRAME_RATE = 60;
    private static final int canvasWidth = 800;
    private static final int canvasHeight = 600;
    private static final double worldWidth = 16.0;  // meters
    private static final double WORLD_HEIGHT = 12.0; // meters
    private static final double scale = canvasWidth / worldWidth; // pixels per meter

    private static final Color backgroundColor = Color.web("#1a1a1a");

    private PhysicsEngine engine;
    private Canvas canvas;
    private GraphicsContext graphicsContext;
    private AnimationTimer gameLoop;

    @Override
    public void start(Stage primaryStage) {
        engine = new PhysicsEngine();

        BorderPane root = new BorderPane();
        canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        graphicsContext = canvas.getGraphicsContext2D();
        root.setCenter(canvas);

        root.setBottom(createControlPanel());

        Scene scene = new Scene(root, CANVAS_WIDTH, CANVAS_HEIGHT + 50);
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
        engine.draw(graphicsContext, backgroundColor, canvasWidth, canvasHeight, scale);
    }

    public static void main(String[] args) {
        launch(args);
    }
}