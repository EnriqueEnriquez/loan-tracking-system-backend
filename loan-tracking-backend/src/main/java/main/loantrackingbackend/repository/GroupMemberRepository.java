package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.embedabble.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {

    List<GroupMember> findByGroup_GroupId(Long groupId);
}
