package com.vehicle.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vehicle.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email); // for future login functionality
}
