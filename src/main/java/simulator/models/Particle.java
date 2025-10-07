package simulator.models;

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

public class Particle {
    private Body body;
    private double radius;
    private double mass;
    private String color;
    private double x, y;
    private double vx, vy;

    public Particle(double x, double y, double radius, double mass) {
        this.radius = radius;
        this.mass = mass;
        this.color = "#FFFFFF";

        this.body = new Body();
        Circle circle = new Circle(radius);
        BodyFixture fixture = new BodyFixture(circle);
        fixture.setDensity(mass / (Math.PI * radius * radius));
        body.addFixture(fixture);
        body.setMass(MassType.NORMAL);
        body.translate(x, y);

        syncFromBody();
    }

    public void syncFromBody() {
        Vector2 position = body.getWorldCenter();
        Vector2 velocity = body.getLinearVelocity();
        this.x = position.x;
        this.y = position.y;
        this.vx = velocity.x;
        this.vy = velocity.y;
    }

    public void applyForce(Vector2 force) {
        body.applyForce(force);
    }

    public void applyImpulse(Vector2 impulse) {
        body.applyImpulse(impulse);
    }

    public void setVelocity(double vx, double vy) {
        body.setLinearVelocity(vx, vy);
    }

    public void setPosition(double x, double y) {
        body.translate(x - this.x, y - this.y);
        this.x = x;
        this.y = y;
    }

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
