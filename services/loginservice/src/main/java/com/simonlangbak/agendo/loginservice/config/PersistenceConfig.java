package com.simonlangbak.agendo.loginservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.simonlangbak.agendo.loginservice")
public class PersistenceConfig {
}

