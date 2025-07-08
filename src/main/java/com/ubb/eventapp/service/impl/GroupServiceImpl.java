package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Group;
import com.ubb.eventapp.repository.GroupRepository;
import com.ubb.eventapp.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
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

    @Override
    @Transactional(readOnly = true)
    public List<Group> findByRepresentativeUser(String userId) {
        return groupRepository.findByRepresentativeUser(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findByTags(Collection<Integer> tagIds) {
        return groupRepository.findDistinctByTagsIdIn(tagIds);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Group> findByName(String name) {
        return groupRepository.findByNombreContainingIgnoreCase(name);
    }
}
