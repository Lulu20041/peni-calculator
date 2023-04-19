package com.example.tamozhpenies.refinancingRate;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
public class RefinancingRateService {
    private static final String API_URL = "https://www.nbrb.by/api/refinancingrate?onDate=" + LocalDateTime.now();
    final RefinancingRateRepo refinancingRateRepo;
    final private RestTemplate restTemplate;
    public RefinancingRateService(RefinancingRateRepo refinancingRateRepo, RestTemplate restTemplate) {
        this.refinancingRateRepo = refinancingRateRepo;
        this.restTemplate = restTemplate;
    }
    public RefinancingRate getRate() {
        ResponseEntity<String> response =
                restTemplate.getForEntity(API_URL,String.class);
        JSONArray jsonArray = new JSONArray(response.getBody());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        double rateValue = jsonObject.getDouble("Value");

        RefinancingRate refinancingRate = new RefinancingRate(rateValue);
        refinancingRateRepo.save(refinancingRate);
        System.out.println(refinancingRate.getRate());

        return refinancingRate;
    }
}
