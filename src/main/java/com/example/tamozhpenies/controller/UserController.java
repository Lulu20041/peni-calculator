package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.clientSum.ClientSumService;
import com.example.tamozhpenies.peni.PeniService;
import com.example.tamozhpenies.user.Role;
import com.example.tamozhpenies.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


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

    @GetMapping("/")
    public String redirect() {
        return "redirect:/login";
    }

    //Авторизация
    @RequestMapping("/default")
    public String defaultPage(HttpServletRequest request, RedirectAttributes redirectAttributes, Principal principal) {
        if (request.isUserInRole("ROLE_" + Role.ADMIN.name())) {
            return "redirect:/admin";
        }
        redirectAttributes.addAttribute("username", principal.getName());
        return "redirect:/client/{username}";
    }

    @GetMapping("/login")
    public String getPage() {
        return "login";
    }

}
