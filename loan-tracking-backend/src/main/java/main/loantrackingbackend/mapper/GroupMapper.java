package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.GroupDto;
import main.loantrackingbackend.entity.Group;

public class GroupMapper {
    public static GroupDto mapToGroupDto(Group group) {
        return new GroupDto(
                group.getGroupId(),
                group.getGroupName()
        );
    }

    public static Group mapToGroup(GroupDto groupDto) {
        return new Group(
                groupDto.getGroupId(),
                groupDto.getGroupName()
        );
    }
}
