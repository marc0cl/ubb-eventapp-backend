package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, String> {
}
