package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.GroupMemberDto;
import main.loantrackingbackend.dto.PersonDto;

import java.util.List;

public interface GroupMemberService {
    GroupMemberDto addMember(Long groupId, Long personId);

    void removeMember(Long groupId, Long personId);
    List<PersonDto> getMembersByGroupId(Long groupId);
}
