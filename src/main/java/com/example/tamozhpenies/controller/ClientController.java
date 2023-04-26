package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.peni.PeniService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class ClientController {
    final PeniService peniService;
    final ClientSumService clientSumService;
    public ClientController(PeniService peniService, ClientSumService clientSumService) {
        this.peniService = peniService;
        this.clientSumService = clientSumService;
    }

    @GetMapping("/{id}")
    public String getPage(@PathVariable("id") Long id, Model model) {
        List<Peni> peniList = peniService.getPeniByUserId(id);
        model.addAttribute("clientPeni",peniList);

        List<ClientSum> clientSums = clientSumService.getSumsOfClient(id);
        model.addAttribute("clientSums", clientSums);
        return "client";
    }
}
