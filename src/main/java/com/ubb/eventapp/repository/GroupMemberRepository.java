package com.ubb.eventapp.repository;

import com.ubb.eventapp.model.GroupMember;
import com.ubb.eventapp.model.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
}
