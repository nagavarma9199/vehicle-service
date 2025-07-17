package com.vehicle.user_service.controller;

import com.vehicle.user_service.dto.*;
import com.vehicle.user_service.dto.BookingResponseDTO;
import com.vehicle.user_service.entity.User;
import com.vehicle.user_service.repository.UserRepository;
import com.vehicle.user_service.Service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserRepository userRepository;

    // ✅ Create booking using DTO
    @PostMapping("/create")
    public BookingResponseDTO createBooking(@RequestBody BookingRequestDTO dto, Principal principal) {
        getLoggedInUser(principal); // for access check (optional)
        return bookingService.createBooking(dto);
    }

    // ✅ Get user bookings as DTOs
    @GetMapping("/my")
    public List<BookingResponseDTO> getMyBookings(Principal principal) {
        User user = getLoggedInUser(principal);
        return bookingService.getBookingsByUser(user);
    }

    // ✅ Cancel booking (no DTO needed here)
    @DeleteMapping("/cancel/{bookingId}")
    public void cancelBooking(@PathVariable Long bookingId, Principal principal) {
        User user = getLoggedInUser(principal);
        bookingService.cancelBooking(bookingId, user);
    }

    // ✅ Admin: Get all bookings
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<BookingResponseDTO> getAllBookings() {
        return bookingService.getAllBookings();
    }

    // ✅ Get user from JWT (Principal)
    private User getLoggedInUser(Principal principal) {
        User user = userRepository.findByEmail(principal.getName());
        if (user == null) throw new RuntimeException("User not found");
        return user;
    }
}
