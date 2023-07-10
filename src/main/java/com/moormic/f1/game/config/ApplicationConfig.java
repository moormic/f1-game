package com.moormic.f1.game.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAutoConfiguration
@ConfigurationProperties
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ApplicationConfig {
    @Autowired
    private final RestClientConfig restClientConfig;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
