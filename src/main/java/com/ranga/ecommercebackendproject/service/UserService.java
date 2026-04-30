package com.ranga.ecommercebackendproject.service;

import com.ranga.ecommercebackendproject.model.User;
import com.ranga.ecommercebackendproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // ✅ GET ALL USERS
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ✅ GET USER BY ID
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    // ✅ CREATE USER (REGISTER)
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 🔐 encrypt
        return userRepository.save(user);
    }

    // ✅ UPDATE USER
    public User updateUser(Long id, User user) {
        user.setId(id);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // 🔐 encrypt
        return userRepository.save(user);
    }

    // ✅ DELETE USER
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // ✅ LOGIN LOGIC
    public String login(String email, String password) {

        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return "User not found";
        }

        if (passwordEncoder.matches(password, user.getPassword())) {
            return "Login successful";
        } else {
            return "Invalid password";
        }
    }
}