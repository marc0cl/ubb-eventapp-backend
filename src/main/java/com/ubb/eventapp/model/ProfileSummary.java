package com.ubb.eventapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Simple DTO aggregating basic profile information for a user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileSummary {
    /**
     * Username displayed for the user profile.
     */
    private String username;
    private long friendsCount;
    private List<Trophy> trophies;
    /**
     * Aggregated statistics about the user's events.
     */
    private ProfileEvents events;
}
