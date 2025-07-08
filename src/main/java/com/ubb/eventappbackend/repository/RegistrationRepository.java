package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Registration;
import com.ubb.eventappbackend.model.RegistrationId;
import com.ubb.eventappbackend.model.RegistrationState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegistrationRepository extends JpaRepository<Registration, RegistrationId> {

    /**
     * Finds event registrations for a user with the specified state.
     *
     * @param userId identifier of the user
     * @param estado state of the registration
     * @return list of registrations that match
     */
    List<Registration> findByUser_IdAndEstado(String userId, RegistrationState estado);
}
