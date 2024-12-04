package com.agrillnovate.System.controllers;

import com.agrillnovate.System.Events.UserSignupEvent;
import com.agrillnovate.System.model.User;
import com.agrillnovate.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(
            @RequestParam("name") String name,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("phone") String phone,
            @RequestParam("address") String address,
            @RequestParam("role") String role,
            @RequestParam(value = "educationBackground", required = false) String educationBackground,
            @RequestParam(value = "cv", required = false) MultipartFile cvFile) {
        try {
            byte[] cvData = cvFile != null ? cvFile.getBytes() : null;
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAddress(address);
            user.setRole(role);
            user.setEducationBackground(educationBackground);
            user.setCv(cvData);

            User savedUser = userService.saveUser(user);
            eventPublisher.publishEvent(new UserSignupEvent(this, savedUser));

            return ResponseEntity.ok(savedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error registering user: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        return userService.updateUser(id, userDetails);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.emailExists(email);
        return ResponseEntity.ok(exists);
    }
    // In your controller
    @GetMapping("/check-phone_exists")
    public ResponseEntity<Boolean> checkPhonenumberExists(@RequestParam String phone) {
        boolean exists = userService.phoneexists(phone);
        return ResponseEntity.ok(exists);
    }


    @GetMapping("/role")
    public ResponseEntity<?> getUserRole(@RequestParam String email) {
        try {
            String role = userService.getUserRole(email);
            return ResponseEntity.ok().body(role);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }

    @GetMapping("/id")
    public ResponseEntity<Long> getUserIdByEmail(@RequestParam String email) {
        User user = userService.findByEmail(email);
        if (user != null) {
            return ResponseEntity.ok(user.getUserID());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = ((UserDetails) authentication.getPrincipal()).getUsername();

        User currentUser = userService.findByEmail(currentUserName);
        if (currentUser != null) {
            return ResponseEntity.ok(currentUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<User> updateUserProfile(@RequestBody User userDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = ((UserDetails) authentication.getPrincipal()).getUsername();

        User currentUser = userService.findByEmail(currentUserName);
        if (currentUser != null) {
            currentUser.setName(userDetails.getName());
            currentUser.setPhone(userDetails.getPhone());
            currentUser.setAddress(userDetails.getAddress());

            User updatedUser = userService.saveUser(currentUser);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
