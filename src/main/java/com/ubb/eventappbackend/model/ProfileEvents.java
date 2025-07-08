package com.ubb.eventappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<CalendarEntry> calendar;
}
