package com.example.tamozhpenies.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepo.findById(id);

        return user;
    }
    public User getUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }
    public List<User> getClients() {
        return userRepo.findAllClients();
    }
    public void saveUser(User user) {
        userRepo.save(user);
    }
    public boolean isUserArleadyExists(String username) {
        User user = userRepo.findByUsername(username);
        System.out.println(user.getUsername());
        if (user != null) {
            return true;
        }
        return false;
    }
}
