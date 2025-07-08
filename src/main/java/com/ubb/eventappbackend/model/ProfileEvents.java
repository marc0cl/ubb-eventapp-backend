package com.ubb.eventappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * DTO aggregating event statistics for a user.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileEvents {
    private long eventsAttended;
    private long eventsCreated;
    /**
     * Number of future events the user is registered to attend.
     */
    private long eventsToAttend;
}
