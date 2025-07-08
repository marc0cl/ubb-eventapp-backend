package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.User;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    User updateProfile(User user);
    Optional<User> findById(String id);
}
