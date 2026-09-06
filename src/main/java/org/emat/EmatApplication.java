package org.emat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Spring Boot Application entry point for EMAT project.
 */
@SpringBootApplication
@EnableAsync
public class EmatApplication {
    public static void main(String[] args) {
        SpringApplication.run(EmatApplication.class, args);
    }
}
