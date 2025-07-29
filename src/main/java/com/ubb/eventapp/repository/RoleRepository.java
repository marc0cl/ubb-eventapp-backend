package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.Role;
import com.ubb.eventapp.model.RoleName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByNombre(RoleName nombre);
}
