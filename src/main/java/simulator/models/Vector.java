package simulator.models;

/**
 * Vector class. Used for positions, accelerations, forces, etc.
 */
public class Vector {
    private int dimension;
    private double[] values;

    public Vector(int dimension) {
        this.dimension = dimension;
        this.values = new double[dimension];
    }

    public Vector(double[] values) {
        this.dimension = values.length;
        this.values = values;
    }

    public static Vector copy(Vector other) {
        return new Vector(other.values.clone());
    }

    public void setValue(int dimension, double value) {
        assert dimension < this.dimension;
        values[dimension] = value;
    }

    public double getValue(int dimension) {
        assert dimension < this.dimension;
        return this.values[dimension];
    }

    public Vector plus(Vector delta) {
        assert delta.dimension == this.dimension;
        Vector result = new Vector(this.dimension);
        for(int dim = 0; dim < this.dimension; dim++) {
            result.setValue(dim, this.getValue(dim) + delta.getValue(dim));
        }
        return result;
    }

    public Vector minus(Vector delta) {
        assert delta.dimension == this.dimension;
        Vector result = new Vector(this.dimension);
        for(int dim = 0; dim < this.dimension; dim++) {
            result.setValue(dim, this.getValue(dim) - delta.getValue(dim));
        }
        return result;
    }

    public Vector divided(double scalar) {
        Vector result = Vector.copy(this);
        for(int dim = 0; dim < this.dimension; dim++) {
            result.setValue(dim, result.getValue(dim) / scalar);
        }
        return result;
    }

    public Vector times(double scalar) {
        Vector result = Vector.copy(this);
        for(int dim = 0; dim < this.dimension; dim++) {
            result.setValue(dim, this.getValue(dim) * scalar);
        }
        return result;
    }

    public double getMagnitude() {
        double norm = 0;
        for(double value: this.values) {
            norm += value;
        }
        return Math.sqrt(norm);
    }

    public Vector getUnit() {
        return this.divided(this.getMagnitude());
    }

    public int getDimension() {
        return this.dimension;
    }
}
