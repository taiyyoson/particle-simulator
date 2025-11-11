package simulator.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class DrawableBody extends Body {
    private static GraphicsContext graphicsContext;
    public static void setGraphicsContext(GraphicsContext gc) {
        graphicsContext = gc;
    }

    private Color fillColor;
    private Color borderColor;
    private Integer borderWeight;

    public DrawableBody(BodyBuilder builder) {
        super(builder);
        this.fillColor = builder.getFillColor();
        this.borderColor = builder.getBorderColor();
        this.borderWeight = builder.getBorderWeight();
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public Integer getBorderWeight() {
        return this.borderWeight;
    }
}
