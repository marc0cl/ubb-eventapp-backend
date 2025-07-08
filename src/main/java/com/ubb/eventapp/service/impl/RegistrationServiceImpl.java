package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Registration;
import com.ubb.eventapp.model.RegistrationId;
import com.ubb.eventapp.model.RegistrationState;
import com.ubb.eventapp.repository.RegistrationRepository;
import com.ubb.eventapp.service.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;

    @Override
    public Registration register(Registration registration) {
        return registrationRepository.save(registration);
    }

    @Override
    public Registration cancelRegistration(RegistrationId id) {
        Registration registration = registrationRepository.findById(id).orElseThrow();
        registration.setEstado(RegistrationState.CANCELADO);
        return registrationRepository.save(registration);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Registration> findById(RegistrationId id) {
        return registrationRepository.findById(id);
    }
}
