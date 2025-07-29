package com.ubb.eventapp.service.impl;

import com.ubb.eventapp.model.Group;
import com.ubb.eventapp.repository.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceImplTest {

    private GroupRepository groupRepository;
    private GroupServiceImpl service;

    @BeforeEach
    void setup() {
        groupRepository = mock(GroupRepository.class);
        service = new GroupServiceImpl(groupRepository);
    }

    @Test
    void findByTags_returnsRepositoryResult() {
        List<Group> groups = List.of(Group.builder().id(1L).build());
        Set<Integer> tagIds = Set.of(1, 2);
        when(groupRepository.findDistinctByTagsIdIn(tagIds)).thenReturn(groups);

        List<Group> result = service.findByTags(tagIds);

        assertEquals(groups, result);
        verify(groupRepository).findDistinctByTagsIdIn(tagIds);
    }
}
