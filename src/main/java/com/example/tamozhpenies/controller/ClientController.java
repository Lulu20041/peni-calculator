package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.peni.PeniService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {
    final PeniService peniService;
    final ClientSumService clientSumService;
    public ClientController(PeniService peniService, ClientSumService clientSumService) {
        this.peniService = peniService;
        this.clientSumService = clientSumService;
    }
    @GetMapping("/")
    public String getPage(Model model) {
        model.addAttribute("username", "");
        return "client";
    }
    @GetMapping("/{username}")
    public String getUserPage(@PathVariable("username") String username, Model model) {
        model.addAttribute("username",username);
        List<Peni> peniList = peniService.getPenies(username);
        model.addAttribute("clientPeni",peniList);

        List<ClientSum> clientSums = clientSumService.getSumsOfClientByName(username);
        model.addAttribute("clientSums", clientSums);
        return "client";
    }



}
