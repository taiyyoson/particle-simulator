package com.simulator.experiments.model;

/**
 * Represents a snapshot of a single particle's state at a specific moment in time.
 * Stores position, velocity, mass, radius, and visual properties.
 */
public class ParticleSnapshot {

    private Integer id;
    private double[] position;    // [x, y] coordinates
    private double[] velocity;    // [vx, vy] components
    private Double mass;
    private Double radius;
    private String color;         // Hex color code (e.g., "#FF6B6B")

    public ParticleSnapshot() {}

    public ParticleSnapshot(Integer id, double[] position, double[] velocity, Double mass, Double radius, String color) {
        this.id = id;
        this.position = position;
        this.velocity = velocity;
        this.mass = mass;
        this.radius = radius;
        this.color = color;
    }

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public double[] getPosition() { return position; }
    public void setPosition(double[] position) { this.position = position; }

    public double[] getVelocity() { return velocity; }
    public void setVelocity(double[] velocity) { this.velocity = velocity; }

    public Double getMass() { return mass; }
    public void setMass(Double mass) { this.mass = mass; }

    public Double getRadius() { return radius; }
    public void setRadius(Double radius) { this.radius = radius; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}
