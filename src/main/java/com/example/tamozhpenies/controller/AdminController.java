package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.user.User;
import com.example.tamozhpenies.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String mainPage(Model model) {
        List<User> clients = userService.getClients();
        model.addAttribute("clients",clients);
        return "admin";
    }
    @GetMapping("/register")
    public String registerPage()
    {
        return "register";
    }
    @PostMapping("/redirect")
    public String redirectToCalc(@ModelAttribute String clientName, Model model) {
        model.addAttribute("clientName", clientName);
        return "redirect:/calc/{clientName}";
    }

}
