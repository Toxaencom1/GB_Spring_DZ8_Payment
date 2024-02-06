package com.taxah.springdz8_payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * AppConfig class is a configuration class used to define Spring beans.
 * It is annotated with @Configuration to indicate that this class contains bean definitions.
 */
@Configuration
public class AppConfig {
    /**
     * This method defines a bean of type RestTemplate.
     * It is annotated with @Bean to indicate that it produces a bean to be managed by the Spring container.
     *
     * @return RestTemplate - an instance of RestTemplate.
     */
    @Bean
    public RestTemplate template() {
        return new RestTemplate();
    }
}
