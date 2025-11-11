package com.simulator.experiments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot entry point for experiment-service
 * Runs REST API on port 8080 for storing simulation experiment dat
 */
@SpringBootApplication
public class ExperimentServiceApplication {

    public static void main(String[] args) {
        //run application
        SpringApplication.run(ExperimentServiceApplication.class, args);
    }
}
