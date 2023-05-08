package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.currency.Currency;
import com.example.tamozhpenies.currency.CurrencyService;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.peni.PeniService;
import com.example.tamozhpenies.user.User;
import com.example.tamozhpenies.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    private final UserService userService;
    public CalcController(CurrencyService currencyService, ClientSumService clientSumService, PeniService peniService, UserService userService) {
        this.currencyService = currencyService;
        this.clientSumService = clientSumService;
        this.peniService = peniService;
        this.userService = userService;
    }
    @GetMapping("/")
    public String getPage(Model model) {
        List<Currency> currencies = currencyService.getCurrencies();
        model.addAttribute("currencies",currencies);
        model.addAttribute("clientName", "");

        return "calculate";
    }
    @GetMapping("/{clientName}")
    public String getUserPage(@PathVariable String clientName, Model model) {
        User user = userService.getUserByUsername(clientName);
        model.addAttribute("clientName", clientName);

        List<Currency> currencies = currencyService.getCurrencies();
        model.addAttribute("currencies",currencies);

        List<ClientSum> clientSum = clientSumService.getSumsOfClientByName(clientName);
        model.addAttribute("clientSums", clientSum);

        double peniSum = peniService.peniSum(clientName);
        model.addAttribute("peniSum", peniSum);

        model.addAttribute("currencyValue", new Currency());

        List<Peni> penies = peniService.getPenies(clientName);
        model.addAttribute("penies", penies);
        return "calculate";
    }
    @PostMapping("/calculate")
    public String calculatePeni(@RequestParam("taxDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                    LocalDate taxDate,
                                @RequestParam("taxSum") double taxSum,
                                @RequestParam("peniDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                    LocalDate peniDate,
                                @RequestParam(value = "clientName",defaultValue = "Ecos") String clientName) {
        System.out.println(clientName);
        peniService.deletePenies();
        peniService.calculatePenies(taxDate, taxSum, peniDate, clientName);
        return "redirect:/admin/calc/";
    }
    @PostMapping("/calculate/{clientName}")
    public String calculateUserPeni(@PathVariable("clientName") String clientName,
                                @RequestParam("taxDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                LocalDate taxDate,
                                @RequestParam("taxSum") double taxSum,
                                @RequestParam("peniDate")
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                LocalDate peniDate) {
        System.out.println(clientName);
        peniService.deletePenies();
        peniService.calculatePenies(taxDate, taxSum, peniDate, clientName);
        return "redirect:/admin/calc/" + clientName;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }
}
