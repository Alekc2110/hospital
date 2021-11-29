package com.my.project.petclinic.hospital.persistence.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class PersistenceConditionConfig {

    @Bean
    @RequestScope
    public RequestBackground getPersistenceCondition() {
        return new RequestBackground();
    }
}
