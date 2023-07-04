package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.peni.PeniService;
import com.example.tamozhpenies.reporting.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    final PeniService peniService;
    final ClientSumService clientSumService;
    final ReportService reportService;

    public ClientController(PeniService peniService, ClientSumService clientSumService, ReportService reportService) {
        this.peniService = peniService;
        this.clientSumService = clientSumService;
        this.reportService = reportService;
    }

    @GetMapping("/{username}")
    public String getUserPage(@PathVariable("username") String username, Model model) {
        model.addAttribute("username",username);

        List<Peni> peniList = peniService.getPenies(username);
        model.addAttribute("clientPeni",peniList);

        double peni = peniService.peniSum(username);
        model.addAttribute("peni", peni);

        List<ClientSum> clientSums = clientSumService.getSumsOfClientByName(username);
        model.addAttribute("clientSums", clientSums);
        return "client";
    }
    //Печать отчёта
    @GetMapping("/{username}/print")
    public String printReport(@PathVariable("username") String username, HttpServletResponse response) throws IOException, JRException {

        response.setContentType("application/pdf");
        String key = "Content-disposition";
        String value = "attachment; filename=Отчёт.pdf";

        response.setHeader(key,value);
        reportService.exportClientReport(username, response);

        return "redirect:/client/{username}";
    }
}
