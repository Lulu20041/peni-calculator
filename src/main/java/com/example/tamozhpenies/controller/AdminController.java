package com.example.tamozhpenies.controller;

import com.example.tamozhpenies.reporting.ReportService;
import com.example.tamozhpenies.user.User;
import com.example.tamozhpenies.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    final UserService userService;
    final ReportService reportService;

    public AdminController(UserService userService, ReportService reportService) {
        this.userService = userService;
        this.reportService = reportService;
    }
    //Главная страница
    @GetMapping
    public String mainPage(Model model) {
        List<User> clients = userService.getClients();
        model.addAttribute("clients",clients);
        model.addAttribute("client",new User());
        return "admin";
    }
    //Перейти на страницу регистрации
    @GetMapping("/register")
    public String registerPage(Model model)
    {
        model.addAttribute("user", new User());
        return "register";
    }
    //Страница всех клиентов
    @GetMapping("/clients")
    public String clientsPage(Model model) {

        List<User> clients = userService.getClients();
        model.addAttribute("clients", clients);
        return "clients";
    }
    //Страница редактирования клиента
    @GetMapping("/clients/edit/{id}")
    public String editPage(@PathVariable Long id,Model model) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));

        model.addAttribute("user", user);
        return "edit";
    }
    //Обновление данные клиента
    @PostMapping("/clients/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute("user") User updatedUser) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));

        user.setUsername(updatedUser.getUsername());
        user.setPassword(userService.encodePassword(user.getPassword()));
        user.setEmail(updatedUser.getEmail());
        user.setRegistrationNumber(updatedUser.getRegistrationNumber());

        userService.saveUser(user);
        return "redirect:/admin/clients";
    }
    //Удаление данных клиента
    @GetMapping("/clients/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id: " + id));

        userService.deleteUser(user);
        return "redirect:/admin/clients";
    }
    //Запрос регистрации клиента
    @PostMapping("/register")
    public String registerUser(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (userService.isUserArleadyExists(user.getUsername())) {
            model.addAttribute("error", "Данный клиент уже существует");
            return "/register";
        }
        if (bindingResult.hasErrors()) {
            return  "/register";
        }

        userService.registerUser(user);

        return "redirect:/admin";
    }

    //Перенаправление на страницу калькулятора
    @PostMapping("/redirect")
    public String redirectToCalc(@RequestParam("clientName") String clientName, RedirectAttributes redirectAttributes)
    {
        if (clientName.isEmpty()) {
            return "redirect:/admin";
        }
        redirectAttributes.addAttribute("clientName", clientName);
        return "redirect:/admin/calc/{clientName}";
    }
}

