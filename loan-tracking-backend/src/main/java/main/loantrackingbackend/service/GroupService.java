package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.GroupDto;

import java.util.List;

public interface GroupService {
    GroupDto createGroup(GroupDto groupDto);

    GroupDto getGroupById(Long groupId);

    List<GroupDto> getAllGroups();

    GroupDto updateGroup(Long groupId, GroupDto groupDto);

    String deleteGroup(Long groupId);

    long countMembers(Long groupId);

}
