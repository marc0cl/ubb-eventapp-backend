package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
