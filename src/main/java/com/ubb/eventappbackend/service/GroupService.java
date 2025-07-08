package com.ubb.eventappbackend.service;

import com.ubb.eventappbackend.model.Group;

import java.util.Optional;

public interface GroupService {
    Group createGroup(Group group);
    Group updateGroup(Group group);
    Optional<Group> findById(String id);
}
