package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
