package com.simulator.experiments.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Map;

/**
 * Flexible experiment data model for storing simulation runs.
 * Supports any physics engine type with extensible parameter/metrics storage.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "experiments")
public class Experiment {

    @Id
    private String id;

    /**
     * Timestamp when experiment was run
     */
    private Instant timestamp;

    /**
     * Type of physics engine used (e.g., "DYNB4J", "NBODY_QUADTREE")
     */
    private String engineType;

    /**
     * Number of particles in simulation
     */
    private Integer particleCount;

    /**
     * Average frames per second during simulation
     */
    private Double avgFPS;

    /**
     * Total computation time in milliseconds
     */
    private Long computeTimeMs;

    /**
     * Flexible parameter storage - engine-specific configuration
     * Examples:
     * - Dyn4j: {gravity: -9.8, damping: 0.1, timeStep: 0.016}
     * - N-body: {gravity: -9.8, theta: 0.5, quadtreeDepth: 8}
     */
    private Map<String, Object> parameters;

    /**
     * Flexible metrics storage - engine-specific performance data
     * Examples:
     * - N-body: {quadtreeNodes: 1523, avgParticlesPerNode: 6.5, collisions: 4523}
     * - General: {energyDrift: 0.002, maxVelocity: 45.2}
     */
    private Map<String, Object> metrics;

    /**
     * Additional metadata for experiment context
     * Examples: {notes: "Testing optimization", version: "1.2.3", author: "Brandon"}
     */
    private Map<String, Object> metadata;
}
