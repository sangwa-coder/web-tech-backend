package com.agrillnovate.System.controllers;

import com.agrillnovate.System.Repository.UserRepository;
import com.agrillnovate.System.model.AuthenticationResponse;
import com.agrillnovate.System.model.User;
import com.agrillnovate.System.security.jwt.JwtUtil;
import com.agrillnovate.System.model.AuthenticationRequest;
import com.agrillnovate.System.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserDetailsService userDetailsService, UserService userService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        System.out.println("Login attempt for identifier: " + authenticationRequest.getIdentifier());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getIdentifier(), authenticationRequest.getPassword())
            );
        } catch (Exception e) {
            System.out.println("Authentication failed for identifier: " + authenticationRequest.getIdentifier() + " with error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect identifier or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getIdentifier());
        final User user = userRepository.findByEmailOrPhone(authenticationRequest.getIdentifier(), authenticationRequest.getIdentifier())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with identifier: " + authenticationRequest.getIdentifier()));

        System.out.println("User role: " + user.getRole());

        final String jwt = jwtUtil.generateToken(userDetails, String.valueOf(user.getUserID()), user.getName(), user.getRole());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("role", user.getRole());

        return ResponseEntity.ok(response);
    }
}
