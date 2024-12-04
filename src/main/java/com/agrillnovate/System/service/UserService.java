package com.agrillnovate.System.service;

import com.agrillnovate.System.model.User;
import com.agrillnovate.System.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated_at(new java.util.Date());
        if ("EXPERT".equalsIgnoreCase(user.getRole())) {
            user.setApproved(false); // Set approved to false for experts
        } else {
            user.setApproved(true); // Automatically approve other roles
        }
        return userRepository.save(user);
    }

    public User authenticateUser(String identifier, String password) {
        return userRepository.findByEmailOrPhone(identifier, identifier)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .orElse(null);
    }

    public String getUserRole(String identifier) {
        User user = userRepository.findByEmailOrPhone(identifier, identifier).orElse(null);
        if (user != null) {
            return user.getRole();
        }
        throw new UsernameNotFoundException("User not found.");
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPhone(userDetails.getPhone());
            user.setAddress(userDetails.getAddress());
            user.setRole(userDetails.getRole());
            if (!userDetails.getPassword().isEmpty()) {
                user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            }
            return userRepository.save(user);
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public boolean phoneexists(String Phone) {return  userRepository.existsByPhone(Phone);}
    public long countUsers() {
        return userRepository.count();
    }
    public List<User> getUsersByRole(String role) {
        return (List<User>) userRepository.findByRole(role);
    }
    public User approveUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        user.setApproved(true);
        return userRepository.save(user);
    }

    public User disableUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        user.setApproved(false);
        return userRepository.save(user);
    }
}
