package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReviewRepository extends JpaRepository<EventReview, String> {
}
