package com.vehicle.user_service.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vehicle.user_service.Service.VehicleService;
import com.vehicle.user_service.dto.AddVehicleDTO;
import com.vehicle.user_service.dto.VehicleDTO;
import com.vehicle.user_service.entity.User;
import com.vehicle.user_service.repository.UserRepository;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Add a new vehicle using DTO
    @PostMapping("/add")
    public VehicleDTO addVehicle(@RequestBody AddVehicleDTO dto, Principal principal) {
        User user = getLoggedInUser(principal);
        return vehicleService.addVehicle(dto, user);
    }

    // ✅ Get all vehicles for the logged-in user
    @GetMapping("/my")
    public List<VehicleDTO> getMyVehicles(Principal principal) {
        User user = getLoggedInUser(principal);
        return vehicleService.getVehiclesByUser(user);
    }

    private User getLoggedInUser(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) throw new RuntimeException("User not found");
        return user;
    }
}
