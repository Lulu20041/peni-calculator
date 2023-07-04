package com.example.tamozhpenies.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    final UserRepository userRepo;
    final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

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
            return user.getRole();
        }
        return null;
    }
    public Optional<User> getUserById(Long id) {
        Optional<User> user = userRepo.findById(id);

        return user;
    }

    public void registerUser(User user) {
        User createdUser = new User(user.getUsername(),
                passwordEncoder.encode(user.getPassword()),
                Role.CLIENT.name(),
                user.getEmail(),
                user.getRegistrationNumber());

        userRepo.save(createdUser);
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
    //Хешировать пароль
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    public void deleteUser(User user) { userRepo.delete(user); }

    public boolean isUserArleadyExists(String username) {
        User user = userRepo.findByUsername(username);

        if (user != null) {
            return true;
        }
        return false;
    }
}
