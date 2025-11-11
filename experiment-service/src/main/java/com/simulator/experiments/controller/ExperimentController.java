package com.simulator.experiments.controller;

import com.simulator.experiments.model.Experiment;
import com.simulator.experiments.repository.ExperimentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

/**
 *  REST API controller for experiment data.
 */
@RestController
@RequestMapping("/experiments")
@CrossOrigin(origins = "*")
public class ExperimentController {

    @Autowired
    private ExperimentRepository repository;


    @GetMapping
    public ResponseEntity<List<Experiment>> getAllExperiments() {
        List<Experiment> experiments = repository.findAll();
        return ResponseEntity.ok(experiments);
    }

    @PostMapping
    public ResponseEntity<Experiment> createExperiment(@RequestBody Experiment experiment) {
        if (experiment.getTimestamp() == null) {
            experiment.setTimestamp(Instant.now());
        }

        Experiment saved = repository.save(experiment);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    
}
