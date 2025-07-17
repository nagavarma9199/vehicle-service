package com.vehicle.user_service.dto;

import java.time.LocalDate;

public class BookingRequestDTO {
    private Long vehicleId;
    private String serviceType;
    private LocalDate bookingDate;

    // Getters and Setters
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
}
