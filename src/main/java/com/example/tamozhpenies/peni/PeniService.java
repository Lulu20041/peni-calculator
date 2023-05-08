package com.example.tamozhpenies.peni;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.refinancingRate.RefinancingRate;
import com.example.tamozhpenies.refinancingRate.RefinancingRateService;
import com.example.tamozhpenies.user.UserService;
import com.example.tamozhpenies.user.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;



@Service
public class PeniService {
    final PeniRepo peniRepo;
    final RefinancingRateService refinancingRateService;
    final ClientSumService clientSumService;
    final UserService userService;
    public PeniService(PeniRepo peniRepo, RefinancingRateService refinancingRateService, ClientSumService clientSumService, UserService userService) {
        this.peniRepo = peniRepo;
        this.refinancingRateService = refinancingRateService;
        this.clientSumService = clientSumService;
        this.userService = userService;
    }
    public void calculatePenies(LocalDate taxDate, double taxSum, LocalDate peniDate,String clientName) {
        User client = userService.getUserByUsername(clientName);
        System.out.println(clientName.length());
        Map<LocalDate,ClientSum> clientSums = new TreeMap<>();
        for (ClientSum clientSum : clientSumService.getSumsOfClientByName(clientName)) {
            clientSums.put(clientSum.getDate(),clientSum);
        }

        RefinancingRate refinancingRate = refinancingRateService.getRate();
        double rate = refinancingRate.getRate();
        if (clientSums.isEmpty()) {
            //период между платежами
            Period period = Period.between(taxDate,peniDate.plusDays(1));

            Peni peni = new Peni();

            int days = (int) ChronoUnit.DAYS.between(taxDate,peniDate);
            System.out.println(taxSum + " " + days + " " + rate);
            double calculatedPeni = (taxSum * days * rate) / (360 * 100);
            peni.setPeniAmount(calculatedPeni);
            peni.setPeriodBegin(taxDate);
            peni.setPeriodEnd(peniDate);
            peni.setAmountOfDays(days);
            peni.setTaxSum(taxSum);
            peni.setRefinancingRate(refinancingRate);
            client.addPeni(peni);
            peniRepo.save(peni);
        }
        else {
            System.out.println(clientSums);
        }

    }
    public List<Peni> getPenies(String name) {
        return peniRepo.findAllByUsername(name);
    }
    public List<Peni> getPeniByUserId(Long id) { return peniRepo.findAllByUserId(id); }
    public void deletePenies() {
        peniRepo.deleteAll();
    }
    public double peniSum(String username) {
        List<Peni> penies = peniRepo.findAllByUsername(username);
        double sum = 0;
        for (Peni peni : penies) {
            sum += peni.getPeniAmount();
        }
        BigDecimal bd = new BigDecimal(sum);
        bd = bd.setScale(6, RoundingMode.HALF_UP);
        sum = bd.doubleValue();
        return sum;
    }
}
