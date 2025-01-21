package com.tekarch.flight.TafBookingService.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        // Return a new RestTemplate bean to be autowired into the service layer
        return new RestTemplate();
    }
}