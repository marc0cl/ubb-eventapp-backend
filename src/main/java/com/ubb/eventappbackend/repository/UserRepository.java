package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
