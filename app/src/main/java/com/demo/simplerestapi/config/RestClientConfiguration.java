package com.demo.simplerestapi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Value("${app.randomApi.url}")
    private String randomApiBaseUrl;

    @Bean
    public RestClient restClient(RestClient.Builder builder){
        return builder.
                requestFactory(new JdkClientHttpRequestFactory())
                .baseUrl(randomApiBaseUrl)
                .build();
    }
}
