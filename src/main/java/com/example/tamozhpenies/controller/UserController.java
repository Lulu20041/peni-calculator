package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.user.User;
import com.example.tamozhpenies.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {
    final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String getPage(@ModelAttribute("user") User user) {
        return "login";
    }
    @PostMapping("/login")
    public String submitLogin(@RequestParam String username, @RequestParam String password) {

        if (!userService.checkCredentials(username, password)) {
            return "redirect:/login?error=true";
        }

        String role = userService.getUserRole(username);
        if (role.equals("Admin"))
            return "redirect:/admin";
        else
            return "redirect:/client";
    }
}
