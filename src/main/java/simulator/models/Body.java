package simulator.models;

import simulator.Vector;

public class Body {
    private int dimension;
    private Vector position;
    private Vector velocity;
    private Vector acceleration;
    private Double mass;

    private Body(Builder builder) {
        this.dimension = builder.dimension;
        this.position = builder.position;
        this.velocity = builder.velocity;
        this.acceleration = builder.acceleration;
        this.mass = builder.mass;
    }

    public Builder builder(int dimension) {
        return new Builder(dimension);
    }

    private static class Builder {
        private int dimension;
        private Vector position = null;
        private Vector velocity = null;
        private Vector acceleration;
        private Double mass = 0.0;

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
            if(this.position.dimension() != this.dimension) {
                throw new IllegalArgumentException("Position must be dimension " + this.dimension + " (currently " + this.position.dimension() + ")");
            }
            if(this.velocity.dimension() != this.dimension) {
                throw new IllegalArgumentException("Velocity must be dimension " + this.dimension + " (currently " + this.velocity.dimension() + ")");
            }
            if(this.acceleration.dimension() != this.dimension) {
                throw new IllegalArgumentException("Acceleration must be dimension " + this.dimension + " (currently " + this.acceleration.dimension() + ")");
            }
            return new Body(this);
        }
    }
}
