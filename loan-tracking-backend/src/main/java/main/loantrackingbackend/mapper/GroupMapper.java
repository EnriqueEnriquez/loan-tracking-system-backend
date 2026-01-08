package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.GroupDto;
import main.loantrackingbackend.entity.Group;
import main.loantrackingbackend.entity.GroupMember;

import java.util.stream.Collectors;

public class GroupMapper {
    public static GroupDto mapToGroupDto(Group group) {
        GroupDto groupDto = new GroupDto();
        groupDto.setGroupId(group.getGroupId());
        groupDto.setGroupName(group.getGroupName());
        groupDto.setGroupMembersList(group.getGroupMembers()
                .stream()
                .map(GroupMember::getPerson)
                .map(PersonMapper::mapToPersonDto)
                .collect(Collectors.toList()));

        return groupDto;
    }

    public static Group mapToGroup(GroupDto groupDto) {
        Group group = new Group();
        group.setGroupId(groupDto.getGroupId());
        group.setGroupName(groupDto.getGroupName());

        return group;
    }
}
