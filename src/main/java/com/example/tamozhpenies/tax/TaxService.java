package com.example.tamozhpenies.tax;

import com.example.tamozhpenies.user.User;
import com.example.tamozhpenies.user.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TaxService {
    private final TaxRepo taxRepo;
    private final UserRepository userRepository;

    public TaxService(TaxRepo taxRepo, UserRepository userRepository) {
        this.taxRepo = taxRepo;
        this.userRepository = userRepository;
    }

    public void addTax(LocalDate taxDate, LocalDate payDate, double taxSum, String clientName) {
        User user = userRepository.findByUsername(clientName);
        Tax tax = user.getTax();
        if (tax == null) {
            tax = new Tax();
        }

        tax.setTaxDate(taxDate);
        tax.setPayDate(payDate);
        tax.setTaxSum(taxSum);
        tax.setUser(user);

        user.setTax(tax);
        taxRepo.save(tax);
    }

    public void deleteUserTax(String clientName) {
        User user = userRepository.findByUsername(clientName);

        Tax userTax = user.getTax();
        if (userTax == null) {
            return;
        }
        user.setTax(null);

        taxRepo.delete(userTax);
    }

    public Tax getUserTax(String clientName) {
        User user = userRepository.findByUsername(clientName);

        return user.getTax();
    }
}
