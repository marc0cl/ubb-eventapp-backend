package com.ubb.eventappbackend.model;

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
    private long friendsCount;
    private long eventsAttended;
    private long eventsCreated;
    private List<Trophy> trophies;
}
