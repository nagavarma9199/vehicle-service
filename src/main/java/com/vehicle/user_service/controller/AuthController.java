package com.vehicle.user_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.user_service.dto.LoginRequest;
import com.vehicle.user_service.entity.User;
import com.vehicle.user_service.repository.UserRepository;
import com.vehicle.user_service.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
      System.out.println("Login password: " + request.getPassword());
      User user = userRepository.findByEmail(request.getEmail());

if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
    String token = jwtUtil.generateToken(user.getEmail());
    System.out.println("User from DB: " + user);
System.out.println("Stored hash: " + user.getPassword());
boolean isPasswordMatch = passwordEncoder.matches(request.getPassword(), user.getPassword());
System.out.println("Password match: " + isPasswordMatch);
    return ResponseEntity.ok().body("JWT Token: " + token);
    
    
}




        return ResponseEntity.status(401).body("Invalid credentials");
    }
}
