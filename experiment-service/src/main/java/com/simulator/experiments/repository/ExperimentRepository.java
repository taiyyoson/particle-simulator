package com.simulator.experiments.repository;

import com.simulator.experiments.model.Experiment;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * MongoDB repository for Experiment entities.
 * Provides CRUD operations and custom queries.
 */
@Repository
public interface ExperimentRepository extends MongoRepository<Experiment, String> {

    /**
     * Find experiments by engine type
     */
    //List<Experiment> findByEngineType(String engineType);

    /**
     * Find experiments within a time range
     */
    //List<Experiment> findByTimestampBetween(Instant start, Instant end);

    /**
     * Find experiments by engine type and particle count
     */
    //List<Experiment> findByEngineTypeAndParticleCount(String engineType, Integer particleCount);

    /**
     * Find all experiments ordered by timestamp descending (most recent first)
     */
    List<Experiment> findAllByOrderByTimestampDesc();
}
