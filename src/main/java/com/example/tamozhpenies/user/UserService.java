package com.example.tamozhpenies.user;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepo;
    public boolean checkCredentials(String username, String password) {
        User user = userRepo.findByUsername(username);
        System.out.println(username);
        if(user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
    public String getUserRole(String username) {
        User user = userRepo.findByUsername(username);
        if (user != null) {
            if (user.isAdmin())
                return "Admin";
            else
                return "Client";
        }
        return null;
    }
}