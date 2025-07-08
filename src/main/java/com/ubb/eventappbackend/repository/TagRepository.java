package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
