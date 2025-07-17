package com.vehicle.user_service.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle.user_service.entity.Booking;
import com.vehicle.user_service.entity.User;
import com.vehicle.user_service.entity.Vehicle;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Fetch all bookings for a specific vehicle
    List<Booking> findByVehicle(Vehicle vehicle);

    // Optional: Fetch all bookings by user through vehicle's owner
    List<Booking> findByVehicleOwner(User owner);
}
