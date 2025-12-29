package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.GroupDto;
import main.loantrackingbackend.dto.GroupMemberDto;

import java.util.List;

public interface GroupService {
    GroupDto createGroup(GroupDto groupDto);

    GroupDto getGroupById(Long groupId);

    List<GroupDto> getAllGroups();

    GroupDto updateGroup(Long groupId, GroupDto groupDto);

    void deleteGroup(Long groupId);


}
