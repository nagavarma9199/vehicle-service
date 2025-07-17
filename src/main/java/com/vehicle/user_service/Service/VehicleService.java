package com.vehicle.user_service.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.vehicle.user_service.dto.*;
import com.vehicle.user_service.entity.User;
import com.vehicle.user_service.entity.Vehicle;
import com.vehicle.user_service.repository.UserRepository;
import com.vehicle.user_service.repository.VehicleRepository;
import java.util.stream.Collectors;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    // Create vehicle from DTO
    public VehicleDTO addVehicle(AddVehicleDTO dto, User owner) {
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setRegistrationNumber(dto.getRegistrationNumber());
        vehicle.setYear(dto.getYear());
        vehicle.setOwner(owner);

        Vehicle saved = vehicleRepository.save(vehicle);
        return mapToDTO(saved);
    }

    // Get all vehicles for a user
    public List<VehicleDTO> getVehiclesByUser(User user) {
    return vehicleRepository.findByOwnerId(user.getId())
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
}

    // Optional helper for future update/delete
    public Vehicle getVehicleById(Long id) {
        return vehicleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
    }

    private VehicleDTO mapToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setMake(vehicle.getMake());
        dto.setModel(vehicle.getModel());
        dto.setRegistrationNumber(vehicle.getRegistrationNumber());
        dto.setYear(vehicle.getYear());
        return dto;
    }
}
