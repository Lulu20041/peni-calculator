package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.user.User;
import com.example.tamozhpenies.user.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("client",new User());
        return "admin";
    }
    @GetMapping("/register")
    public String registerPage(@ModelAttribute("user") User user)
    {
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        User user = new User(username, password);
        if (userService.isUserArleadyExists(user.getUsername())) {
            model.addAttribute("error", "Данный клиент уже существует");

            return "/register";
        }
        userService.saveUser(user);

        return "redirect:/admin";
    }
    @GetMapping("/redirect")
    public String redirectToCalc(@RequestParam("clientName") String clientName)
    {
        if (clientName.isEmpty()) {
            return "redirect:/admin";
        }
        return "redirect:/calc/{clientName}";
    }

}
