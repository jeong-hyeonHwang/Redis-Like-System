package com.jhh.core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.jhh.core.domain"
)
public class SpringDataJpaConfig {
}
