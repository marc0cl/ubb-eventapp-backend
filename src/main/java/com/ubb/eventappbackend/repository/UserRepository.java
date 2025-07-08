package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByCorreoUbb(String correoUbb);
}
