package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Registration;
import com.ubb.eventappbackend.model.RegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration, RegistrationId> {
}
