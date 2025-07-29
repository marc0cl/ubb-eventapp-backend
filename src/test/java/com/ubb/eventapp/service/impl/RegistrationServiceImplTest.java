package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Registration;
import com.ubb.eventapp.model.RegistrationId;
import com.ubb.eventapp.model.RegistrationState;
import com.ubb.eventapp.repository.RegistrationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RegistrationServiceImplTest {

    private RegistrationRepository registrationRepository;
    private RegistrationServiceImpl service;

    @BeforeEach
    void setup() {
        registrationRepository = mock(RegistrationRepository.class);
        service = new RegistrationServiceImpl(registrationRepository);
    }

    @Test
    void cancelRegistration_setsStateToCanceledAndSaves() {
        RegistrationId id = new RegistrationId(1L, 2L);
        Registration reg = Registration.builder().id(id).estado(RegistrationState.INSCRITO).build();
        when(registrationRepository.findById(id)).thenReturn(Optional.of(reg));
        when(registrationRepository.save(any(Registration.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Registration result = service.cancelRegistration(id);

        assertEquals(RegistrationState.CANCELADO, result.getEstado());
        ArgumentCaptor<Registration> captor = ArgumentCaptor.forClass(Registration.class);
        verify(registrationRepository).save(captor.capture());
        assertEquals(RegistrationState.CANCELADO, captor.getValue().getEstado());
    }
}
