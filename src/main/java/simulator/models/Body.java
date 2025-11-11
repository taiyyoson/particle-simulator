package simulator.models;

import java.util.List;
import java.util.concurrent.Phaser;

/**
 * Body class - used to represent round physical bodies in a given dimension.
 */
public class Body {
    private static Double G = Double.parseDouble("6.67430e-11");

    private int dimension;
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Double mass;
    private Double radius;

    /**
     * Private constructor for builder pattern
     * @param builder builder object to create Body from
     */
    public Body(BodyBuilder builder) {
        this.dimension = builder.getDimension();
        this.position = builder.getPosition();
        this.velocity = builder.getVelocity();
        this.acceleration = builder.getAcceleration();
        this.mass = builder.getMass();
        this.radius = builder.getRadius();
    }

    /**
     * Dimension getter
     * @return the dimension of the body
     */
    public int getDimension() {
        return this.dimension;
    }

    /**
     * Mass getter
     * @return mass of body
     */
    public Double getMass() {
        return this.mass;
    }

    /**
     * Radius getter
     * @return radius of bod+ y
     */
    public Double getRadius() {
        return this.radius;
    }

    /**
     * Position updater
     * @param deltaPosition the vector by which to update the position
     */
    public void updatePosition(Vector deltaPosition) {
        this.position = this.position.plus(deltaPosition);
    }

    /**
     * Position getter
     * @return the position of the body
     */
    public Vector getPosition() {
        return this.position;
    }

    /**
     * Velocity updater
     * @param deltaVelocity the vector by which to update the velocity
     */
    public void updateVelocity(Vector deltaVelocity) {
        this.velocity = this.velocity.plus(deltaVelocity);
    }

    /**
     * Velocity getter
     * @return the velocity of the body
     */
    public Vector getVelocity() {
        return this.velocity;
    }

    /**
     * Acceleration updater
     * @param deltaAcceleration the vector by which to update the acceleration
     */
    public void updateAcceleration(Vector deltaAcceleration) {
        this.acceleration = this.acceleration.plus(deltaAcceleration);
    }

    /**
     * Acceleration getter
     * @return the acceleration of the body
     */
    public Vector getAcceleration() {
        return this.acceleration;
    }

    /**
     * Compute the magnitude of the gravitational force towards another body
     * @param attractor another body which interacts with this gravitationally
     * @return the magnitude of the graviational force towards the other body
     */
    public Double getGravitationalForceMagnitudeTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        Double radius = attractor.position.minus(this.position).getMagnitude();
        return G * (this.mass - attractor.mass) / radius;
    }

    /**
     * Compute the gravitational force towards another body as a vector
     * @param attractor anothe body which interacts with this gravitationally
     * @return the gravitational force towards the other body
     */
    public Vector getGravitationalForceTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        Vector deltaPosition = attractor.position.minus(this.position);
        return deltaPosition.getUnit().times(getGravitationalForceMagnitudeTowards(attractor));
    }

    public Vector getNetGravitationalForce(List<? extends Body> bodies) {
        Vector netGravitationalForce = new Vector(this.dimension);
        for(Body body: bodies) {
            assert body.dimension == this.dimension;
            if(body != this) {
                netGravitationalForce = netGravitationalForce.plus(this.getGravitationalForceTowards(body));
            }
        }
        return netGravitationalForce;
    }

    public void update(Double timestep, List<? extends Body> bodies, Phaser phaser) {
        System.out.println("Timestep: " + timestep);
        System.out.println("Initial state: ");
        System.out.println("\tPosition: " + this.position);
        System.out.println("\tVelocity: " + this.velocity);
        System.out.println("\tAcceleration: " + this.acceleration);
        Vector netGravitationalForce = this.getNetGravitationalForce(bodies);
        phaser.arriveAndAwaitAdvance();
        updateAcceleration(netGravitationalForce.times(timestep));
        updateVelocity(this.acceleration.times(timestep));
        updatePosition(this.velocity.times(timestep));
        System.out.println("Final state: ");
        System.out.println("\tPosition: " + this.position);
        System.out.println("\tVelocity: " + this.velocity);
        System.out.println("\tAcceleration: " + this.acceleration);
        phaser.arriveAndDeregister();
    }
}
