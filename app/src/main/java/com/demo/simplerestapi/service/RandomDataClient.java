package com.demo.simplerestapi.service;

import com.demo.simplerestapi.model.ApplianceData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
@RequiredArgsConstructor
public class RandomDataClient {

    private final RestClient restClient;


    public ApplianceData randomAppliance() {
        return restClient.get()
                .uri("/appliances")
                .retrieve()
                .body(ApplianceData.class);
    }
}
