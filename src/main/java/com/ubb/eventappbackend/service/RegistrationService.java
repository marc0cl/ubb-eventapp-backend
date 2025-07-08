package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.Registration;
import com.ubb.eventappbackend.model.RegistrationId;

import java.util.Optional;

public interface RegistrationService {
    Registration register(Registration registration);
    Registration cancelRegistration(RegistrationId id);
    Optional<Registration> findById(RegistrationId id);
}
