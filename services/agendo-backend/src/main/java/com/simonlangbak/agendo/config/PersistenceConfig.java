package com.simonlangbak.agendo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.simonlangbak.agendo")
public class PersistenceConfig {
}

