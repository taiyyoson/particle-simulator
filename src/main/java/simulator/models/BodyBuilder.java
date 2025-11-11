package simulator.models;

import javafx.scene.paint.Color;

/**
 * Builder for both Body and DrawableBody.
 */
public class BodyBuilder {
    private int dimension;
    private Double mass = 0.0;
    private Double radius = 0.0;
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Color fillColor = Color.WHITE;
    private Color borderColor = Color.WHITE;
    private Integer borderWeight = 0;

    /**
     * Constructor - set dimension and default values for vectors (0 vector).
     * @param dimension
     */
    public BodyBuilder(int dimension) {
        this.dimension = dimension;
        this.position = new Vector(dimension);
        this.velocity = new Vector(dimension);
        this.acceleration = new Vector(dimension);
    }

    /**
     * Dimension getter
     * @return dimension of the BodyBuilder
     */
    public int getDimension() {
        return this.dimension;
    }

    /**
     * Mass setter
     * @param mass to which to set the mass of the body
     * @return BodyBuilder instance to allow chaining
     */
    public BodyBuilder setMass(double mass) {
        this.mass = mass;
        return this;
    }

    /**
     * Mass getter
     * @return mass of the BodyBuilder
     */
    public Double getMass() {
        return this.mass;
    }

    /**
     * Radius setter
     * @param radius to which to set the mass of the body
     * @return BodyBuilder instance to allow chaining
     */
    public BodyBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    /**
     * Radius getter
     * @return the radius of the BodyBuilder
     */
    public Double getRadius() {
        return this.radius;
    }

    /**
     * Position setter
     * @param position to which to set the position of the body
     * @return BodyBuilder instance to allow chaining
     */
    public BodyBuilder setPosition(Vector position) {
        this.position = position;
        return this;
    }

    /**
     * Position getter
     * @return the position of the BodyBuilder
     */
    public Vector getPosition() {
        return this.position;
    }

    /**
     * Velocity setter
     * @param velocity to which to set the velocity of the body
     * @return BodyBuilder instance to allow chaining
     */
    public BodyBuilder setVelocity(Vector velocity) {
        this.velocity = velocity;
        return this;
    }

    /**
     * Velocity getter
     * @return the velocity of the BodyBuilder
     */
    public Vector getVelocity() {
        return this.velocity;
    }

    /**
     * Acceleration setter
     * @param acceleration the acceleration to which to set the acceleration of the body
     * @return BodyBuilder instance to allow chaining
     */
    public BodyBuilder setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    /**
     * Acceleration getter
     * @return the acceleration of the BodyBuilder
     */
    public Vector getAcceleration() {
        return this.acceleration;
    }

    public BodyBuilder setFillColor(Color fillColor) {
        this.fillColor = fillColor;
        return this;
    }

    public Color getFillColor() {
        return this.fillColor;
    }

    public BodyBuilder setBorderColor(Color borderColor) {
        this.borderColor = borderColor;
        return this;
    }

    public Color getBorderColor() {
        return this.borderColor;
    }

    public BodyBuilder setBorderWeight(Integer borderWeight) {
        this.borderWeight = borderWeight;
        return this;
    }

    public Integer getBorderWeight() {
        return borderWeight;
    }

    /**
     * Private helper method to validate parameters necessary to create Body instance
     */
    private void validateBody() {
        if(this.dimension <= 0) {
            throw new IllegalArgumentException("Dimension must be positive (dimension is " + this.dimension + ")");
        }
        if(this.position.getDimension() != this.dimension) {
            throw new IllegalArgumentException("Position must be dimension " + this.dimension + " (currently " + this.position.getDimension() + ")");
        }
        if(this.velocity.getDimension() != this.dimension) {
            throw new IllegalArgumentException("Velocity must be dimension " + this.dimension + " (currently " + this.velocity.getDimension() + ")");
        }
        if(this.acceleration.getDimension() != this.dimension) {
            throw new IllegalArgumentException("Acceleration must be dimension " + this.dimension + " (currently " + this.acceleration.getDimension() + ")");
        }
        if(this.radius < 0) {
            throw new IllegalArgumentException("Radius must be positive (currently " + this.radius + ")");
        }
    }

    /**
     * Body factory method
     * @return a new Body created from this BodyBuilder
     */
    public Body buildBody() {
        validateBody();
        return new Body(this);
    }

    /**
     * Private helper method to validate parameters necessary to create DrawableBody instnace
     */
    private void validateDrawable() {}

    /**
     * DrawableBody factory method
     * @return a new DrawableBody created from this BodyBuilder
     */
    public DrawableBody buildDrawableBody() {
        validateBody();
        validateDrawable();
        return new DrawableBody(this);
    }
}
