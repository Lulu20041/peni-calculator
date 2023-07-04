package com.example.tamozhpenies.peni;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.refinancingRate.RefinancingRate;
import com.example.tamozhpenies.refinancingRate.RefinancingRateService;
import com.example.tamozhpenies.user.UserService;
import com.example.tamozhpenies.user.User;
import jakarta.transaction.Transactional;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@Transactional
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
        Map<LocalDate,ClientSum> clientSums = new TreeMap<>();
        for (ClientSum clientSum : clientSumService.getSumsOfClientByName(clientName)) {
            clientSums.put(clientSum.getDate(),clientSum);
        }

        RefinancingRate refinancingRate = refinancingRateService.getRate();
        double rate = refinancingRate.getRate();

        //Пеня начислена в сегодняшний день или более ранний
        boolean payDateBeforeEndDate = false;

        //В случае если клиент не вносил уплаты
        if (clientSums.isEmpty()) {

            Peni peni = new Peni();

            //Количество дней между датой начисления налога и датой расчёта таможенной пени
            int days = (int) ChronoUnit.DAYS.between(taxDate,peniDate);
            //Таможенная пеня
            double calculatedPeni = (taxSum * days * rate) / (360 * 1);
            peni.setPeniAmount(DoubleRounder.round(calculatedPeni,2));
            peni.setPeriodBegin(taxDate);
            peni.setPeriodEnd(peniDate);
            peni.setAmountOfDays(days);
            peni.setTaxSum(taxSum);
            peni.setRefinancingRate(refinancingRate);

            client.addPeni(peni);
            peniRepo.save(peni);
        }
        else {
            for (Map.Entry<LocalDate, ClientSum> entry : clientSums.entrySet()) {
                //Дата уплаты клиента
                LocalDate payDate = entry.getKey();

                Peni peni = new Peni();

                ClientSum clientSum = entry.getValue();

                //Уплата клиента, переведённая в BYN
                double paymentSum = clientSum.getSum() * clientSum.getCurrency().getOfficialRate();

                if (peniDate.isBefore(payDate)) {
                    payDate = peniDate;
                    payDateBeforeEndDate = false;
                }

                int days = (int) ChronoUnit.DAYS.between(taxDate, payDate);

                peni.setPeriodBegin(taxDate);
                peni.setPeriodEnd(payDate);
                peni.setRefinancingRate(refinancingRate);
                peni.setAmountOfDays(days);
                //Расчёт пени
                double calculatedPeni = (taxSum * days * rate) / (360 * 1);
                System.out.println(taxSum);
                peni.setTaxSum(taxSum);
                taxSum -= paymentSum;

                peni.setPeniAmount(DoubleRounder.round(calculatedPeni,2));

                if (payDateBeforeEndDate)
                    break;

                client.addPeni(peni);
                peniRepo.save(peni);
            }
        }
    }
    public List<Peni> getPenies(String name) {
        return peniRepo.findAllByUsername(name);
    }

    public List<Peni> getPeniByUserId(Long id) { return peniRepo.findAllByUserId(id); }

    public void deletePenies() {
        peniRepo.deleteAll();
    }

    public void deletePeniesOfClient(String username) {
        User user = userService.getUserByUsername(username);
        peniRepo.deleteByUser(user);
    }

    public List<Peni> getAllByUsername(String username) {
        return peniRepo.findAllByUsername(username);
    }

    public double peniSum(String username) {
        List<Peni> penies = peniRepo.findAllByUsername(username);
        double sum = 0;
        for (Peni peni : penies) {
            sum += peni.getPeniAmount();
        }
        BigDecimal bd = new BigDecimal(sum);
        //Округление пени
        bd = bd.setScale(6, RoundingMode.HALF_UP);
        sum = bd.doubleValue();
        return sum;
    }
}
