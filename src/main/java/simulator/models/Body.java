package simulator.models;

import simulator.Vector;

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

    private Body(Builder builder) {
        this.dimension = builder.dimension;
        this.position = builder.position;
        this.velocity = builder.velocity;
        this.acceleration = builder.acceleration;
        this.mass = builder.mass;
        this.radius = builder.radius;
    }

    public Double getMass() {
        return this.mass;
    }

    public Double getRadius() {
        return this.radius;
    }

    public int getDimension() {
        return this.dimension;
    }

    public void updatePosition(Vector deltaPosition) {
        this.position = this.position.plus(deltaPosition);
    }

    public Vector getPosition() {
        return this.position;
    }

    public void updateVelocity(Vector deltaVelocity) {
        this.velocity = this.velocity.plus(deltaVelocity);
    }

    public Vector getVelocity() {
        return this.velocity;
    }

    public void updateAcceleration(Vector deltaAcceleration) {
        this.acceleration = this.acceleration.plus(deltaAcceleration);
    }

    public Vector getAcceleration() {
        return this.acceleration;
    }

    public Double getGraviationalForceMagnitudeTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        Double radius = this.position.minus(attractor.position).getMagnitude();
        return G * (this.mass - attractor.mass) / radius;
    }

    public Vector getGraviationalForceTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        Vector deltaPosition = attractor.position.minus(this.position);
        return deltaPosition.times(getGraviationalForceMagnitudeTowards(attractor));
    }

    public Double getGraviationalAccelerationMagnitudeTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        return getGraviationalForceMagnitudeTowards(attractor) / this.mass;
    }

    public Vector getGravitationalAccelerationTowards(Body attractor) {
        assert this.dimension == attractor.dimension;
        return getGraviationalForceTowards(attractor);
    }

    public Builder builder(int dimension) {
        return new Builder(dimension);
    }

    private static class Builder {
        private int dimension;
        private Double mass = 0.0;
        private Double radius = 0.0;
        private Vector position;
        private Vector velocity;
        private Vector acceleration;

        private Builder(int dimension) {
            this.dimension = dimension;
            this.position = new Vector(dimension);
            this.velocity = new Vector(dimension);
            this.acceleration = new Vector(dimension);
        }

        public Builder setMass(double mass) {
            this.mass = mass;
            return this;
        }

        public Builder setRadius(double radius) {
            this.radius = radius;
            return this;
        }

        public Builder setPosition(Vector position) {
            this.position = position;
            return this;
        }

        public Builder setVelocity(Vector velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder setAcceleration(Vector acceleration) {
            this.acceleration = acceleration;
            return this;
        }

        public Body build() {
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
            return new Body(this);
        }
    }
}
