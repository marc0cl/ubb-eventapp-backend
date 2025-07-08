package com.ubb.eventappbackend.service.impl;

import com.ubb.eventappbackend.model.Group;
import com.ubb.eventappbackend.repository.GroupRepository;
import com.ubb.eventappbackend.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    @Override
    public Group createGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    public Group updateGroup(Group group) {
        return groupRepository.save(group);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Group> findById(String id) {
        return groupRepository.findById(id);
    }
}
