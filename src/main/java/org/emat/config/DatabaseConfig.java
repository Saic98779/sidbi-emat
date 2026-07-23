package org.emat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Database configuration for Oracle.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.emat.repository")
public class DatabaseConfig {
    // Spring Boot auto-configuration and application.properties handle JPA/DataSource.
}
