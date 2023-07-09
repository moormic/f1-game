package com.moormic.f1.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
    public RestTemplate restClient() {
        return new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            var headers = request.getHeaders();
            headers.add("X-RapidAPI-Key", restClientConfig.getApiKey());
            return execution.execute(request, body);
        })).build();
    }
}
