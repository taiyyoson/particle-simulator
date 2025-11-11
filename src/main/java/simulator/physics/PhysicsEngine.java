package simulator.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulator.models.DrawableBody;
import simulator.models.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class PhysicsEngine {
    private Boolean running = true;
    private static Phaser phaser = new Phaser();
    ExecutorService executorService = Executors.newCachedThreadPool();

    private List<DrawableBody> bodies = new LinkedList();

    public PhysicsEngine() {}

    public PhysicsEngine(List<DrawableBody> bodies) {
        this.bodies = bodies;
    }

    public void start() {
        this.running = true;
    }

    public void pause() {
        this.running = false;
    }

    public Boolean isRunning() {
        return this.running;
    }

    public void addBody(DrawableBody body) {
        bodies.add(body);
    }

    public void update(Double timeStep) {
        System.out.println("Timestep: " + timeStep);
        phaser.bulkRegister(bodies.size() + 1);
        bodies.forEach(body -> executorService.submit(() -> {
            body.update(timeStep, bodies, phaser);
        }));
        phaser.arriveAndAwaitAdvance();
        phaser.arriveAndDeregister();
    }

    public void draw(
            GraphicsContext graphicsContext,
            Color backgroundColor,
            Integer canvasWidth,
            Integer canvasHeight,
            Double scale
    ) {
        if(!running) {
            return;
        }
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0, 0, canvasWidth, canvasHeight);

        bodies.forEach(body -> {
            Vector position = body.getPosition();
            Double screenX = position.getValue(0) * scale;
            Double screenY = canvasHeight - (position.getValue(1) * scale);
            double screenRadius = body.getRadius() * scale;
            System.out.println("Drawing body at " + screenX + ", " + screenY);

            graphicsContext.setFill(body.getFillColor());
            graphicsContext.fillOval(
                    screenX - screenRadius,
                    screenY - screenRadius,
                    screenRadius * 2 ,
                    screenRadius * 2
            );

            graphicsContext.setStroke(body.getBorderColor());
            graphicsContext.setLineWidth(body.getBorderWeight());
            graphicsContext.strokeOval(
                    screenX - screenRadius,
                    screenY - screenRadius,
                    screenRadius * 2,
                    screenRadius * 2
            );
        });
    }
}