package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSum;
import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.peni.Peni;
import com.example.tamozhpenies.peni.PeniService;
import com.example.tamozhpenies.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {
    final UserService userService;
    final PeniService peniService;
    final ClientSumService clientSumService;
    public UserController(UserService userService, PeniService peniService, ClientSumService clientSumService) {
        this.userService = userService;
        this.peniService = peniService;
        this.clientSumService = clientSumService;
    }
    @GetMapping("")
    public String redirect() {
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String getPage() {
        return "login";
    }
    @PostMapping("/enter")
    public String submitLogin(@RequestParam String username, @RequestParam String password, Model model) {

        if (!userService.checkCredentials(username, password)) {
            return "redirect:/login?error=true";
        }

        String role = userService.getUserRole(username);
        if (role.equals("Admin"))
            return "redirect:/admin";

        model.addAttribute("username", username);
        return "redirect:/client/{username}";
    }

}
