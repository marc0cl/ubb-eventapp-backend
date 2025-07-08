package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Campus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CampusRepository extends JpaRepository<Campus, Integer> {
}
