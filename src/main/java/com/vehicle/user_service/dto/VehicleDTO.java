package com.vehicle.user_service.dto;


public class VehicleDTO {
    private Long id;
    private String make;
    private String model;
    private String registrationNumber;
    private int year;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }
}
