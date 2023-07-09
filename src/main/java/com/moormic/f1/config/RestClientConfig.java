package com.moormic.f1.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(value = "rest-client")
@EnableConfigurationProperties
public class RestClientConfig {
    private String apiKey;
}
