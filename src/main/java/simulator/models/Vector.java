package simulator.models;

/**
 * Vector class. Used for positions, accelerations, forces, etc.
 */
public class Vector {
    private static double epsilon = Double.parseDouble("1e-20");
    private int dimension;
    private double[] values;

    public Vector(int dimension) {
        this.dimension = dimension;
        this.values = new double[dimension];
        for(int i = 0; i < dimension; i++) {
            this.values[i] = 0;
        }
    }

    public Vector(double[] values) {
        this.dimension = values.length;
        this.values = new double[values.length];
        for(int i = 0; i < dimension; i++) {
            this.values[i] = values[i];
        }
    }

    public boolean equals(Vector other) {
        if(this.dimension != other.dimension) {
            return false;
        }
        for(int dim = 0; dim < this.dimension; dim++) {
            if(epsilon < this.values[dim] - other.values[dim]) {
                return true;
            }
        }
        return false;
    }


    public static Vector copy(Vector other) {
        return new Vector(other.values);
    }

    public Vector setValue(int dimension, double value) {
        assert dimension < this.dimension;
        values[dimension] = value;
        return this;
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
            norm += value * value;
        }
        return Math.sqrt(norm);
    }

    public Vector getUnit() {
        return this.divided(this.getMagnitude());
    }

    public int getDimension() {
        return this.dimension;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for(int dim = 0; dim < dimension; dim++) {
            stringBuilder.append(this.getValue(dim));
            if(dim != dimension - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }
}
