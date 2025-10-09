package view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import simulator.Listener;
import simulator.models.Particle;
import simulator.physics.PhysicsEngine;

public class Window implements Listener {
    private PhysicsEngine physicsEngine;
    private GraphicsContext graphicsContext;
    private double scale;
    private int canvasWidth;
    private int canvasHeight;
    private Color backgroundColor;
    private Color borderColor;
    private int borderWidth;

    public static class Builder {
        private PhysicsEngine physicsEngine;
        private GraphicsContext graphicsContext;
        private double scale;
        private int canvasWidth;
        private int canvasHeight;
        private Color backgroundColor = Color.web("1a1a1a");
        private Color borderColor = Color.WHITE;
        private int borderWidth = 1;

        public Builder(
                PhysicsEngine physicsEngine,
                GraphicsContext graphicsContext,
                double scale,
                int canvasWidth, int canvasHeight) {
            this.physicsEngine = physicsEngine;
            this.graphicsContext = graphicsContext;
            this.scale = scale;
            this.canvasWidth = canvasWidth;
            this.canvasHeight = canvasHeight;
        }

        public void setBackgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public void setBorderColor(Color borderColor) {
            this.borderColor = borderColor;
        }

        public void setBorderWidth(int borderWidth) {
            this.borderWidth = borderWidth;
        }

        public Window build() {
            return new Window(
                    physicsEngine,
                    graphicsContext,
                    scale,
                    canvasWidth, canvasHeight,
                    backgroundColor,
                    borderColor, borderWidth
            );
        }
    }

    public static Builder builder(
            PhysicsEngine physicsEngine,
            GraphicsContext graphicsContext,
            double scale,
            int canvasWidth, int canvasHeight
    ) {
        return new Builder(
                physicsEngine,
                graphicsContext,
                scale,
                canvasWidth, canvasHeight
        );
    }

    private Window(
            PhysicsEngine physicsEngine,
            GraphicsContext graphicsContext,
            double scale,
            int canvasWidth, int canvasHeight,
            Color backgroundColor,
            Color borderColor, int borderWidth) {
        this.physicsEngine = physicsEngine;
        this.graphicsContext = graphicsContext;
        this.scale = scale;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.backgroundColor = backgroundColor;
        this.borderColor = borderColor;
        this.borderWidth = borderWidth;
    }

    @Override
    public void render() {
        graphicsContext.setFill(backgroundColor);
        graphicsContext.fillRect(0, 0, canvasWidth, canvasHeight);

        for(Particle particle: physicsEngine.getParticles()) {
            double screenX = particle.getX() * scale;
            double screenY = canvasHeight - particle.getY() * scale;
            double screenRadius = particle.getRadius() * scale;

            graphicsContext.setFill(Color.web(particle.getColor()));
            graphicsContext.fillOval(
                    screenX - screenRadius,
                    screenY - screenRadius,
                    screenRadius * 2,
                    screenRadius * 2
            );

            graphicsContext.setStroke(borderColor);
            graphicsContext.setLineWidth(borderWidth);
            graphicsContext.strokeOval(
                    screenX - screenRadius,
                    screenY - screenRadius,
                    screenRadius * 2,
                    screenRadius * 2
            );
        }
    }
}
