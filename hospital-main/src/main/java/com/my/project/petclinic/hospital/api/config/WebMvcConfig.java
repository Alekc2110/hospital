package com.my.project.petclinic.hospital.api.config;

import com.my.project.petclinic.hospital.api.filter.IsJdbcHeaderInterceptor;
import com.my.project.petclinic.hospital.persistence.config.RequestBackground;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private RequestBackground condition;

    @Bean
    public IsJdbcHeaderInterceptor getPersistenceInterceptor(){
        return new IsJdbcHeaderInterceptor(condition);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPersistenceInterceptor());
    }
}
