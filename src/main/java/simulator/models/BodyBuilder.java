package simulator.models;

public class BodyBuilder {
    private int dimension;
    private Double mass = 0.0;
    private Double radius = 0.0;
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private String color;

    public BodyBuilder(int dimension) {
        this.dimension = dimension;
        this.position = new Vector(dimension);
        this.velocity = new Vector(dimension);
        this.acceleration = new Vector(dimension);
    }

    public BodyBuilder setMass(double mass) {
        this.mass = mass;
        return this;
    }

    public BodyBuilder setRadius(double radius) {
        this.radius = radius;
        return this;
    }

    public BodyBuilder setPosition(Vector position) {
        this.position = position;
        return this;
    }

    public BodyBuilder setVelocity(Vector velocity) {
        this.velocity = velocity;
        return this;
    }

    public BodyBuilder setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    public BodyBuilder setColor(String color) {
        this.color = color;
        return this;
    }

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

    public Body buildBody() {
        validateBody();
        return new Body(this);
    }

    private void validateDrawable() {
        if(this.color == null || this.color.isEmpty() || this.color.isBlank()) {
            throw new IllegalArgumentException("Invalid color for DrawableBody");
        }
    }

    public DrawableBody buildDrawableBody() {
        validateBody();
        validateDrawable();
        return new DrawableBody(this);
    }
}
