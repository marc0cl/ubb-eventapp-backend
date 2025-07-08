package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Registration;
import com.ubb.eventapp.model.RegistrationId;

import java.util.Optional;

public interface RegistrationService {
    Registration register(Registration registration);
    Registration cancelRegistration(RegistrationId id);
    Optional<Registration> findById(RegistrationId id);
}
