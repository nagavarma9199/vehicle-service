package com.vehicle.user_service.Service;

import java.util.List;
import java.util.Optional;

import com.vehicle.user_service.entity.User;

public interface UserService {
    User registerUser(User user);
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    void deleteUser(Long id);
}