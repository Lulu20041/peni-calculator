package com.example.tamozhpenies.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String mainPage() {
        return "admin";
    }
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

}