package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.EventReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventReviewRepository extends JpaRepository<EventReview, Integer> {
}
