package com.vehicle.user_service.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.user_service.entity.User;
import com.vehicle.user_service.entity.Vehicle;
import com.vehicle.user_service.repository.UserRepository;
import com.vehicle.user_service.repository.VehicleRepository;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private UserRepository userRepository;

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public List<Vehicle> getVehiclesByOwnerId(Long ownerId) {
        return vehicleRepository.findByOwnerId(ownerId);
    }

    public void deleteVehicle(Long id) {
        vehicleRepository.deleteById(id);
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle); // saves if exists
    }

    // 🔥 New method to link vehicle to owner
    public Vehicle createVehicleWithOwner(Long ownerId, Vehicle vehicle) {
        User owner = userRepository.findById(ownerId)
            .orElseThrow(() -> new RuntimeException("Owner not found with ID: " + ownerId));
        vehicle.setOwner(owner);
        return vehicleRepository.save(vehicle);
    }
}
