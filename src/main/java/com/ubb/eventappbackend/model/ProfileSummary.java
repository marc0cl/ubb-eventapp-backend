package com.ubb.eventappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Aggregated view of a user's profile information.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileSummary {
    private long friendCount;
    private long attendedEvents;
    private long createdEvents;
    private List<Trophy> trophies;
}
