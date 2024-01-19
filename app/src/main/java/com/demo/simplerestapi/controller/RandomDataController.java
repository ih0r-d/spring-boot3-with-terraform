package com.demo.simplerestapi.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.lviv.javaclub.clients.model.ApplianceData;
import ua.lviv.javaclub.clients.repository.RandomDataRepository;
import ua.lviv.javaclub.clients.service.RandomDataClient;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/random")
public class RandomDataController {
    private final RandomDataClient randomDataClient;

    private final RandomDataRepository randomDataRepository;

    @GetMapping("/appliance/{id}")
    public ResponseEntity<ApplianceData> randomAppliance(@PathVariable Long id){
        var applianceDataOptional = randomDataRepository.getById(id);
        return applianceDataOptional.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/appliance")
    public ResponseEntity<Map<String, Long>> randomAppliance(){
        var applianceData = randomDataClient.randomAppliance();
        var applianceId = randomDataRepository.createRandomAppliance(applianceData);
        return ResponseEntity.ok(Map.of("id",applianceId));
    }


    @GetMapping("/appliance/data")
    public ResponseEntity<List<ApplianceData>> allAppliances(){
        var appliances = randomDataRepository.getApplianceData();
        return ResponseEntity.ok(appliances);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> updateUser(@PathVariable Long id){
        var optionalData = randomDataRepository.removeDataById(id);
        return optionalData.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
