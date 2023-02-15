package com.user.service.service;

import com.user.service.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
public interface UserService {

    // Create User
    UserEntity saveUser(UserEntity user);
    // Get all users
    List<UserEntity> getAllUsers();
    // Get user by ID
    UserEntity getUserById(String id);
}
