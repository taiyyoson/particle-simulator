package simulator.models;

import java.util.List;
import java.util.concurrent.Phaser;

/**
 * Body class - used to represent round physical bodies in a given dimension.
 */
public class Body {
    private static Double G = Double.parseDouble("6.67430e-11");
    private static Integer nextId = 1;

    private int dimension;
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Double mass;
    private Double radius;
    private Integer id = nextId++;

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

    public void setAcceleration(Vector acceleration) {
        this.acceleration = acceleration;
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
     * @return the magnitude of the gravitational force towards the other body
     */
    public Double getGravitationalForceMagnitudeTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        Double radius = Math.abs(attractor.position.minus(this.position).getMagnitude());
        return G * this.mass * attractor.mass / radius;
    }

    /**
     * Compute the gravitational force towards another body as a vector
     * @param attractor another body which interacts with this gravitationally
     * @return the gravitational force towards the other body
     */
    public Vector getGravitationalForceTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        Vector deltaPosition = attractor.position.minus(this.position);
        System.out.println(this.id + "->" + attractor.id + "=" + deltaPosition);
        return deltaPosition.getUnit().times(getGravitationalForceMagnitudeTowards(attractor));
    }

    public Vector getNetGravitationalForce(List<? extends Body> bodies) {
        Vector netGravitationalForce = new Vector(this.dimension);
        for(Body body: bodies) {
            assert body.dimension == this.dimension;
            if(!body.equals(this)) {
                netGravitationalForce = netGravitationalForce.plus(this.getGravitationalForceTowards(body));
            }
        }
        return netGravitationalForce;
    }

    public void update(Double timestep, List<? extends Body> bodies, Phaser phaser) {
        Vector gravitationalAcceleration = this.getNetGravitationalForce(bodies).divided(this.mass);

        StringBuilder before = new StringBuilder()
                .append("Initial state for particle " + id  + ": ")
                .append(System.lineSeparator())
                .append("\tPosition: " + this.position)
                .append(System.lineSeparator())
                .append("\tVelocity: " + this.velocity)
                .append(System.lineSeparator())
                .append("\tAcceleration: " + this.acceleration)
                .append(System.lineSeparator())
                .append("\tGravitational acceleration: " + gravitationalAcceleration)
                .append(System.lineSeparator());
        System.out.println(before);

        phaser.arriveAndAwaitAdvance();

        setAcceleration(gravitationalAcceleration.times(timestep));
        updateVelocity(this.acceleration.times(timestep));
        updatePosition(this.velocity.times(timestep));

        StringBuilder after = new StringBuilder()
                .append("Final state for particle " + id + ": ")
                .append(System.lineSeparator())
                .append("\tPosition: " + this.position)
                .append(System.lineSeparator())
                .append("\tVelocity: " + this.velocity)
                .append(System.lineSeparator())
                .append("\tAcceleration: " + this.acceleration)
                .append(System.lineSeparator());
        System.out.println(after);

        phaser.arriveAndDeregister();
    }
}
