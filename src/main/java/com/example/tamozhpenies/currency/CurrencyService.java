package com.example.tamozhpenies.currency;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class CurrencyService {
    private static final String API_URL = "https://www.nbrb.by/api/exrates/rates?periodicity=0";
    private final RestTemplate restTemplate;

    public CurrencyService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public List<Currency> getCurrencies() {
        ResponseEntity<Currency[]> response =
                restTemplate.getForEntity(API_URL, Currency[].class);
        return Arrays.asList(response.getBody());

    }
}
