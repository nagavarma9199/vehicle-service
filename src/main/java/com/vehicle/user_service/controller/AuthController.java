package com.vehicle.user_service.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.user_service.dto.AuthResponseDTO;
import com.vehicle.user_service.dto.LoginRequest;
import com.vehicle.user_service.dto.RegisterRequestDTO;
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

    // ✅ Login Endpoint with AuthResponseDTO
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        com.vehicle.user_service.entity.User user = userRepository.findByEmail(request.getEmail());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            // ✅ Wrap domain user into Spring Security's UserDetails
            UserDetails userDetails = new User(
                    user.getEmail(),
                    user.getPassword(),
                    Collections.emptyList() // No roles yet
            );

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());


            AuthResponseDTO response = new AuthResponseDTO(token, user.getEmail(), user.getRole());
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // ✅ Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDTO request) {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        com.vehicle.user_service.entity.User user = new com.vehicle.user_service.entity.User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER"); // Default role

        userRepository.save(user);

        // 🔥 Generate JWT after successful registration
        UserDetails userDetails = new User(
                user.getEmail(),
                user.getPassword(),
                Collections.emptyList()
        );

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        AuthResponseDTO response = new AuthResponseDTO(token, user.getEmail(), user.getRole());
        return ResponseEntity.ok(response);
    }
}
