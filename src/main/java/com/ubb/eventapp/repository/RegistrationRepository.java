package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.Registration;
import com.ubb.eventapp.model.RegistrationId;
import com.ubb.eventapp.model.RegistrationState;
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
    List<Registration> findByUser_IdAndEstado(Long userId, RegistrationState estado);

    /**
     * Deletes all registrations associated with the provided event.
     *
     * @param eventId identifier of the event
     */
    void deleteByEvent_Id(Long eventId);
}
