package com.ubb.eventappbackend.repository;

import com.ubb.eventappbackend.model.GroupMember;
import com.ubb.eventappbackend.model.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
}
