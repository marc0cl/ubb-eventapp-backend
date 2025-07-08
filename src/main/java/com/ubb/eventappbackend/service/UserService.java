package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.User;
import com.ubb.eventappbackend.model.ProfileSummary;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    User updateProfile(User user);
    Optional<User> findById(String id);

    /**
     * Returns aggregated profile data for the given user.
     *
     * @param userId identifier of the user
     * @return summary object with counts and trophies
     */
    ProfileSummary getProfileSummary(String userId);
}
