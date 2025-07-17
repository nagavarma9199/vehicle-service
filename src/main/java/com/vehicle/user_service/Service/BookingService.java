package com.vehicle.user_service.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vehicle.user_service.dto.BookingRequestDTO;
import com.vehicle.user_service.dto.BookingResponseDTO;
import com.vehicle.user_service.entity.Booking;
import com.vehicle.user_service.entity.User;
import com.vehicle.user_service.entity.Vehicle;
import com.vehicle.user_service.repository.BookingRepository;
import com.vehicle.user_service.repository.VehicleRepository;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    // Create Booking using DTO
    public BookingResponseDTO createBooking(BookingRequestDTO dto) {
        Optional<Vehicle> optionalVehicle = vehicleRepository.findById(dto.getVehicleId());
        if (optionalVehicle.isEmpty()) {
            throw new RuntimeException("Vehicle not found");
        }

        Vehicle vehicle = optionalVehicle.get();

        Booking booking = new Booking();
        booking.setVehicle(vehicle);
        booking.setServiceType(dto.getServiceType());
        booking.setBookingDate(dto.getBookingDate());
        booking.setStatus("PENDING");

        Booking saved = bookingRepository.save(booking);
        return mapToResponseDTO(saved);
    }

    // Get bookings for a user
    public List<BookingResponseDTO> getBookingsByUser(User user) {
        List<Booking> bookings = bookingRepository.findByVehicleOwner(user);
        return bookings.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    // Cancel a booking
    public void cancelBooking(Long bookingId, User user) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getVehicle().getOwner().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized to cancel this booking");
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
    }

    // Admin: Get all bookings
    public List<BookingResponseDTO> getAllBookings() {
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream().map(this::mapToResponseDTO).collect(Collectors.toList());
    }

    // Mapping method: Entity → DTO
    private BookingResponseDTO mapToResponseDTO(Booking booking) {
        BookingResponseDTO dto = new BookingResponseDTO();
        dto.setId(booking.getId());
        dto.setServiceType(booking.getServiceType());
        dto.setBookingDate(booking.getBookingDate());
        dto.setStatus(booking.getStatus());
        dto.setVehicleModel(booking.getVehicle().getModel());
        dto.setVehicleRegistrationNumber(booking.getVehicle().getRegistrationNumber());
        return dto;
    }
}
