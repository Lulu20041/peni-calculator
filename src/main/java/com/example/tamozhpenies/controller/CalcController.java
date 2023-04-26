package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.currency.Currency;
import com.example.tamozhpenies.currency.CurrencyService;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.peni.PeniService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/calc")
public class CalcController {
    private final CurrencyService currencyService;
    private final ClientSumService clientSumService;
    private final PeniService peniService;
    public CalcController(CurrencyService currencyService, ClientSumService clientSumService, PeniService peniService) {
        this.currencyService = currencyService;
        this.clientSumService = clientSumService;
        this.peniService = peniService;
    }
    @GetMapping
    public String getPage(Model model) {
        List<Currency> currencies = currencyService.getCurrencies();
        model.addAttribute(currencies);

        List<ClientSum> clientSum = clientSumService.getSums();
        model.addAttribute("clientSums", clientSum);

        double peniSum = peniService.peniSum();
        model.addAttribute("peniSum", peniSum);

        model.addAttribute("clientSum", new ClientSum());
        model.addAttribute("currencyValue", new Currency());

        List<Peni> penies = peniService.getPenies();
        model.addAttribute("penies", penies);
        return "calk";
    }
    @GetMapping("/{id}")
    public String getUserPage(@PathVariable Long id, Model model) {
        List<Currency> currencies = currencyService.getCurrencies();
        model.addAttribute(currencies);

        List<ClientSum> clientSum = clientSumService.getSums();
        model.addAttribute("clientSums", clientSum);

        double peniSum = peniService.peniSum();
        model.addAttribute("peniSum", peniSum);

        model.addAttribute("clientSum", new ClientSum());
        model.addAttribute("currencyValue", new Currency());

        List<Peni> penies = peniService.getPenies();
        model.addAttribute("penies", penies);
        return "calk";
    }
    @PostMapping("/calculate")
    public String calculatePeni(@RequestParam("taxDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                    LocalDate taxDate,
                                @RequestParam("taxSum") double taxSum,
                                @RequestParam("peniDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                    LocalDate peniDate) {
        peniService.deletePenies();
        peniService.calculatePenies(taxDate, taxSum, peniDate);
        return "redirect:/admin/calc";
    }
}
