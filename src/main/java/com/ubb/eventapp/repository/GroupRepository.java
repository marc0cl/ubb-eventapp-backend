package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Collection;

public interface GroupRepository extends JpaRepository<Group, Long> {

    /**
     * Finds groups whose {@code nombre} contains the provided text, ignoring case.
     *
     * @param nombre part of the group name to search for
     * @return list of groups with matching names
     */
    List<Group> findByNombreContainingIgnoreCase(String nombre);

    /**
     * Finds groups where the given user is registered as representative.
     *
     * @param userId id of the representative user
     * @return list of groups represented by the user
     */
    @Query("select gm.group from GroupMember gm where gm.user.id = :userId and gm.rolGrupo = com.ubb.eventapp.model.GroupRole.REPRESENTANTE")
    List<Group> findByRepresentativeUser(@Param("userId") Long userId);

    /**
     * Finds groups that are associated with any of the provided tag ids.
     *
     * @param tagIds list of tag ids to search for
     * @return list of groups having at least one of the tags
     */
    List<Group> findDistinctByTagsIdIn(Collection<Integer> tagIds);
}
