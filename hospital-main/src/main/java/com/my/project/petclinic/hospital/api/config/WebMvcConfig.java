package com.my.project.petclinic.hospital.api.config;

import com.my.project.petclinic.hospital.api.filter.IsJdbcHeaderInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {


    @Bean
    @RequestScope
    public RequestBackground getPersistenceCondition() {
        return new RequestBackground();
    }

    @Bean
    public IsJdbcHeaderInterceptor getPersistenceInterceptor(RequestBackground condition){
        return new IsJdbcHeaderInterceptor(condition);
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPersistenceInterceptor(getPersistenceCondition()));
    }


}
