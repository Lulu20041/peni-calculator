package com.example.tamozhpenies.peni;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.refinancingRate.RefinancingRate;
import com.example.tamozhpenies.refinancingRate.RefinancingRateService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

@Service
public class OldPeniService {
    final PeniRepo peniRepo;
    final RefinancingRateService refinancingRateService;
    final ClientSumService clientSumService;
    public OldPeniService(PeniRepo peniRepo, RefinancingRateService refinancingRateService, ClientSumService clientSumService) {
        this.peniRepo = peniRepo;
        this.refinancingRateService = refinancingRateService;
        this.clientSumService = clientSumService;
    }
    public void calculatePenies(LocalDate taxDate, double taxSum, LocalDate peniDate) {

        Map<LocalDate, ClientSum> treeMap = new TreeMap<>();
        for (ClientSum clientSum : clientSumService.getSums()) {
            treeMap.put(clientSum.getDate(),clientSum);
        }

        double peni = 0;

        //Ставки рефинансирования
        RefinancingRate refinancingRate = refinancingRateService.getRate();
        double rate = refinancingRate.getRate();

        //Пеня начислена в сегодняшний день или более ранний
        boolean payDateBeforeEndDate = false;

        System.out.println("Peni calculated");
        for (Map.Entry<LocalDate, ClientSum> entry : treeMap.entrySet()) {
            //Дата платежа клиента
            LocalDate payDate = entry.getKey();
            //Пеня
            Peni payment = new Peni();
            //Невыплаченное налоговое обязательство
            payment.setTaxSum(taxSum);
            peni = taxSum;
            //Помножение на ставку рефинансирования
            peni *= rate;
            payment.setRefinancingRate(refinancingRate);

            ClientSum clientSum = entry.getValue();

            double paymentSum = clientSum.getSum();
            taxSum -= paymentSum;
            if (peniDate.isBefore(payDate)) {
                payDate = peniDate;
                payDateBeforeEndDate = true;
            }
            payment.setPeriodBegin(taxDate);
            payment.setPeriodEnd(payDate);


            Period period = Period.between(taxDate,payDate);

            int days = period.getDays();
            payment.setAmountOfDays(days);

            peni *= days;
            peni /= (360 * 100);

            BigDecimal bd = new BigDecimal(peni);
            bd = bd.setScale(6, RoundingMode.HALF_UP);
            peni = bd.doubleValue();

            taxDate = LocalDate.of(taxDate.getYear(),taxDate.getMonth(), taxDate.getDayOfMonth());

            payment.setPeniAmount(peni);

            if (payDateBeforeEndDate)
                break;

            peniRepo.save(payment);
        }
    }
    public List<Peni> getPenies() {
        return peniRepo.findAll();
    }
    public void deletePenies() {
        peniRepo.deleteAll();
    }
    public double peniSum() {
        List<Peni> penies = peniRepo.findAll();
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
