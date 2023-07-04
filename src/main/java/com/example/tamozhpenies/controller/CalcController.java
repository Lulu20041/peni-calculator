package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.currency.Currency;
import com.example.tamozhpenies.currency.CurrencyService;
import com.example.tamozhpenies.dto.ClientSumDAO;
import com.example.tamozhpenies.dto.PeniDAO;
import com.example.tamozhpenies.dto.TaxDAO;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.peni.PeniService;
import com.example.tamozhpenies.tax.Tax;
import com.example.tamozhpenies.tax.TaxService;
import com.example.tamozhpenies.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/admin/calc")
public class CalcController {

    private final CurrencyService currencyService;
    private final ClientSumService clientSumService;
    private final PeniService peniService;
    private final TaxService taxService;


    public CalcController(CurrencyService currencyService, ClientSumService clientSumService, PeniService peniService, UserService userService, TaxService taxService) {
        this.currencyService = currencyService;
        this.clientSumService = clientSumService;
        this.peniService = peniService;
        this.taxService = taxService;
    }
    //Главная страница
    @GetMapping("/{clientName}")
    public String getUserPage(@PathVariable String clientName, Model model) {

        if (!model.containsAttribute("clientSumDao")){
            model.addAttribute("clientSumDao",new ClientSumDAO());
        }
        if (!model.containsAttribute("peniDAO")){
            model.addAttribute("peniDAO",new PeniDAO());
        }

        if (!model.containsAttribute("taxDAO")) {
            model.addAttribute("taxDAO", new TaxDAO());
        }

        model.addAttribute("clientName", clientName);

        List<Currency> currencies = currencyService.getCurrencies();
        model.addAttribute("currencies",currencies);

        System.out.println(currencies.size());

        List<ClientSum> clientSum = clientSumService.getSumsOfClientByName(clientName);
        model.addAttribute("clientSums", clientSum);

        double peniSum = peniService.peniSum(clientName);
        model.addAttribute("peniSum", peniSum);

        Double taxValue = 0.00;
        Tax userTax = taxService.getUserTax(clientName);
        if (userTax != null) {
            Double taxSum = userTax.getTaxSum();
            if (taxSum != null) {
                taxValue = taxSum;
            }
        }

        model.addAttribute("taxValue", taxValue);

        model.addAttribute("currencyValue", new Currency());

        List<Peni> penies = peniService.getPenies(clientName);
        model.addAttribute("penies", penies);

        return "calc";
    }

    //Расчёт пени
    @PostMapping("/calculate/{clientName}")
    public String calculateUserPeni(@PathVariable("clientName") String clientName,
                                    @Validated @ModelAttribute("peniDAO") PeniDAO peniDAO,
                                    BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        Tax tax = taxService.getUserTax(clientName);
        peniService.deletePeniesOfClient(clientName);
        System.out.println(tax);
        System.out.println(peniDAO.getPeniDate());
        peniService.calculatePenies(tax.getPayDate(), tax.getTaxSum(), peniDAO.getPeniDate(), clientName);
        return "redirect:/admin/calc/{clientName}";
    }

    //Добавление уплаты клиента
    @PostMapping("/payment/{clientName}")
    public String addPayment(@PathVariable String clientName,
                             @Validated @ModelAttribute("clientSumDao") ClientSumDAO clientSumDAO,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes)  {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.clientSumDao", bindingResult);
            redirectAttributes.addFlashAttribute("clientSumDao", clientSumDAO);
            return "redirect:/admin/calc/{clientName}";
        }

        clientSumService.addSum(clientSumDAO.getDate(), clientSumDAO.getSum(), clientSumDAO.getCurrencyId(), clientName);
        return "redirect:/admin/calc/{clientName}";
    }

    //Добавление налога клиента
    @PostMapping("/tax/{clientName}")
    public String addTax(@PathVariable("clientName") String clientName,
                         @Validated @ModelAttribute("taxDAO") TaxDAO taxDAO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.taxDAO",bindingResult);
            redirectAttributes.addFlashAttribute("taxDAO",taxDAO);
            return "redirect:/admin/calc/{clientName}";
        }
        taxService.addTax(taxDAO.getTaxDate(), taxDAO.getPayDate(), taxDAO.getTaxSum(), clientName);
        return "redirect:/admin/calc/{clientName}";
    }

    //Удаление уплаты клиента
    @GetMapping("/deleteSum/{clientName}/{id}")
    public String deleteClientSum(@PathVariable("id") Long id,
                                  @PathVariable("clientName") String clientName) {
        clientSumService.deleteClientSum(id);

        return "redirect:/admin/calc/{clientName}";
    }
    //Очистка пени клиента
    @PostMapping("/clear/{clientName}")
    public String deleteClientPeni(@PathVariable("clientName") String clientName) {
        peniService.deletePeniesOfClient(clientName);
        return "redirect:/admin/calc/{clientName}";
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

}
