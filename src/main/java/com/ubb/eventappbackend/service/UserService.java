package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.User;
import com.ubb.eventappbackend.model.ProfileSummary;

import java.util.Optional;

public interface UserService {
    User registerUser(User user);
    User updateProfile(User user);
    Optional<User> findById(String id);

    /**
     * Aggregates some statistics for the user's profile page.
     *
     * @param userId id of the user to summarize
     * @return summary object with counts and trophies
     */
    ProfileSummary getProfileSummary(String userId);
}
