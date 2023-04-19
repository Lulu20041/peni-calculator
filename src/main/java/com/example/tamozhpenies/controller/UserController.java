package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.user.User;
import com.example.tamozhpenies.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String getPage(@ModelAttribute("user") User user) {
        return "index";
    }
    @PostMapping("/login")
    public String submitLogin(@RequestParam String username, @RequestParam String password) {

        if (!userService.checkCredentials(username, password)) {
            return "redirect:/?error=true";
        }

        String role = userService.getUserRole(username);
        if (role.equals("Admin"))
            return "redirect:/admin";
        else
            return "redirect:/client";
    }
}
