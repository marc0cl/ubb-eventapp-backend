package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, String> {

    /**
     * Finds groups whose {@code nombre} contains the provided text, ignoring case.
     *
     * @param nombre part of the group name to search for
     * @return list of groups with matching names
     */
    List<Group> findByNombreContainingIgnoreCase(String nombre);
}
