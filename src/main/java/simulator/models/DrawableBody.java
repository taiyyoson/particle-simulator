package simulator.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableBody extends Body {
    private static GraphicsContext graphicsContext;
    public static void setGraphicsContext(GraphicsContext gc) {
        graphicsContext = gc;
    }

    private Color color;

    public DrawableBody(BodyBuilder builder) {
        super(builder);
        this.color = builder.getColor();
    }

    public void draw() {
    }
}
