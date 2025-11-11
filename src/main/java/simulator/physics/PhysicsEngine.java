package simulator.physics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulator.models.DrawableBody;
import simulator.models.Vector;

import java.util.LinkedList;
import java.util.List;

public class PhysicsEngine {
    private List<DrawableBody> bodies = new LinkedList();

    public PhysicsEngine() {}

    public PhysicsEngine(List<DrawableBody> bodies) {
        this.bodies = bodies;
    }

    public void update(Double timeStep) {

    }

    public void draw(
            GraphicsContext graphicsContext,
            Color backgroundColor,
            Integer canvasWidth,
            Integer canvasHeight,
            Double scale
    ) {
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0, 0, canvasWidth, canvasHeight);

        bodies.forEach(body -> {
            Vector position = body.getPosition();
            Double screenX = position.getValue(0) * scale;
            Double screenY = canvasHeight - (position.getValue(1) * scale);
            double screenRadius = body.getRadius() * scale;

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