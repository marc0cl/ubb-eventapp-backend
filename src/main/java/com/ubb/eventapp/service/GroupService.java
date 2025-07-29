package com.ubb.eventapp.service;

import com.ubb.eventapp.model.Group;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GroupService {
    Group createGroup(Group group);
    Group updateGroup(Group group);
    Optional<Group> findById(Long id);

    /**
     * Retrieves groups represented by the specified user.
     *
     * @param userId the representative user id
     * @return list of groups where the user is representative
     */
    List<Group> findByRepresentativeUser(Long userId);

    /**
     * Retrieves groups that contain any of the provided tags.
     *
     * @param tagIds collection of tag ids
     * @return list of groups having at least one of the tags
     */
    List<Group> findByTags(Collection<Integer> tagIds);

    /**
     * Searches for groups whose name contains the provided text.
     *
     * @param name part of the name to look for
     * @return list of groups matching the name
     */
    List<Group> findByName(String name);
}
