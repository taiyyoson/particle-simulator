package simulator.models;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

/**
 * Particle represents a single particle in the simulation.
 * Wraps a Dyn4j Body with additional properties.
 */
public class Particle {
    private Body body;
    private double radius;
    private double mass;

    // Visual properties
    private String color;

    // Cached position/velocity for rendering
    private double x, y;
    private double vx, vy;

    public Particle(double x, double y, double radius, double mass) {
        this.radius = radius;
        this.mass = mass;
        this.color = "#FFFFFF"; // Default white

        // Create Dyn4j body
        this.body = new Body();

        // Create circular fixture
        Circle circle = new Circle(radius);
        BodyFixture fixture = new BodyFixture(circle);
        fixture.setDensity(mass / (Math.PI * radius * radius)); // Density = mass/area
        body.addFixture(fixture);

        // Set mass type and position
        body.setMass(MassType.NORMAL);
        body.translate(x, y);

        // Initialize cached values
        syncFromBody();
    }

    /**
     * Sync cached position/velocity from Dyn4j body
     * Call this after each physics update
     */
    public void syncFromBody() {
        Vector2 position = body.getWorldCenter();
        Vector2 velocity = body.getLinearVelocity();

        this.x = position.x;
        this.y = position.y;
        this.vx = velocity.x;
        this.vy = velocity.y;
    }

    /**
     * Apply a force to this particle
     */
    public void applyForce(Vector2 force) {
        body.applyForce(force);
    }

    /**
     * Apply an impulse (instant velocity change)
     */
    public void applyImpulse(Vector2 impulse) {
        body.applyImpulse(impulse);
    }

    /**
     * Set the particle's velocity directly
     */
    public void setVelocity(double vx, double vy) {
        body.setLinearVelocity(vx, vy);
    }

    /**
     * Set the particle's position directly
     */
    public void setPosition(double x, double y) {
        body.translate(x - this.x, y - this.y);
        this.x = x;
        this.y = y;
    }

    // Getters and setters
    public Body getBody() { return body; }
    public double getRadius() { return radius; }
    public double getMass() { return mass; }
    public double getX() { return x; }
    public double getY() { return y; }
    public double getVx() { return vx; }
    public double getVy() { return vy; }
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
