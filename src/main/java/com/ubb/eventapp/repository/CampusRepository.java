package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Integer> {
}
