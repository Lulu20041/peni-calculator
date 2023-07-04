package com.example.tamozhpenies.currency;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private static final String API_URL = "https://api.nbrb.by/exrates/rates?periodicity=0";

    private final RestTemplate restTemplate;
    private final CurrencyRepo currencyRepo;

    public CurrencyService(RestTemplate restTemplate, CurrencyRepo currencyRepo) {
        this.restTemplate = restTemplate;
        this.currencyRepo = currencyRepo;
    }

    public List<Currency> getCurrencies() {
        //Парсинг JSON в DTO в класс валюты
        ResponseEntity<CurrencyDTO[]> response =
                restTemplate.getForEntity(API_URL, CurrencyDTO[].class);
        //Валюты, допустимые в системе
        List<String> desiredCurrencies = Arrays.asList("USD","RUB","EUR","PLN","CNY","KZT","BYN");

        List<CurrencyDTO> currencyDTOList = new ArrayList<>(List.of(response.getBody()));
        //Валюта BYN
        CurrencyDTO BYN = new CurrencyDTO(200L, LocalDate.now(),"BYN",1, "Белорусский рубль", 1);
        currencyDTOList.add(BYN);

        List<CurrencyDTO> filteredCurrencies = currencyDTOList.stream()
                .filter(currencyDTO -> desiredCurrencies.contains(currencyDTO.Cur_Abbreviation()))
                .toList();

        List<Currency> currencies = new ArrayList<>();
        //Если в базе данных уже хранится валюта, то в базе данных обновляется лишь его значение.
        //В противном случае в базу данных заносится лишь
        for (CurrencyDTO currencyDTO : filteredCurrencies) {
            String abbr = currencyDTO.Cur_Abbreviation();
            Currency existingCurrency = currencyRepo.findByName(abbr);
            Currency currency;

            if (existingCurrency != null) {
                currency = existingCurrency;
            } else {
                currency = new Currency();
                currency.setName(currencyDTO.Cur_Abbreviation());
                currency.setId(currencyDTO.Cur_ID());
            }
            currency.setOfficialRate(currencyDTO.Cur_OfficialRate());
            currencies.add(currency);
            currencyRepo.save(currency);
        }
        return currencies;
    }

    public Currency findCurrencyById(Long currencyId) {

        Currency currency =  currencyRepo.findById(currencyId)
                .orElseThrow();

        return currency;
    }
}
