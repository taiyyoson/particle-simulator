package com.simulator.experiments.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Flexible experiment data model for storing simulation runs.
 * Supports any physics engine type with extensible parameter/metrics storage.
 * Stores complete particle state snapshots for N-body simulations.
 */
@Document(collection = "experiments")
public class Experiment {

    @Id
    private String id;
    private Instant timestamp;
    private String engineType;
    private Integer particleCount;
    private Double avgFPS;
    private Long computeTimeMs;


    private List<ParticleSnapshot> particles;

    private Map<String, Object> parameters;
    private Map<String, Object> metrics;
    private Map<String, Object> metadata;

    public Experiment() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Instant getTimestamp() { return timestamp; }
    public void setTimestamp(Instant timestamp) { this.timestamp = timestamp; }

    public String getEngineType() { return engineType; }
    public void setEngineType(String engineType) { this.engineType = engineType; }

    public Integer getParticleCount() { return particleCount; }
    public void setParticleCount(Integer particleCount) { this.particleCount = particleCount; }

    public Double getAvgFPS() { return avgFPS; }
    public void setAvgFPS(Double avgFPS) { this.avgFPS = avgFPS; }

    public Long getComputeTimeMs() { return computeTimeMs; }
    public void setComputeTimeMs(Long computeTimeMs) { this.computeTimeMs = computeTimeMs; }

    public Map<String, Object> getParameters() { return parameters; }
    public void setParameters(Map<String, Object> parameters) { this.parameters = parameters; }

    public Map<String, Object> getMetrics() { return metrics; }
    public void setMetrics(Map<String, Object> metrics) { this.metrics = metrics; }

    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }

    public List<ParticleSnapshot> getParticles() { return particles; }
    public void setParticles(List<ParticleSnapshot> particles) { this.particles = particles; }
}
