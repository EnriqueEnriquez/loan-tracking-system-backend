package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.GroupDto;
import main.loantrackingbackend.dto.GroupMemberDto;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.entity.Group;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.entity.embedabble.GroupMemberId;

import static main.loantrackingbackend.mapper.GroupMapper.mapToGroup;
import static main.loantrackingbackend.mapper.GroupMapper.mapToGroupDto;
import static main.loantrackingbackend.mapper.PersonMapper.mapToPerson;
import static main.loantrackingbackend.mapper.PersonMapper.mapToPersonDto;

public class GroupMemberMapper {

    public static GroupMemberDto mapToGroupMemberDto(GroupMember groupMember) {

        GroupMemberId id = groupMember.getId();
        PersonDto personDto = mapToPersonDto(groupMember.getPerson());

        Group group = groupMember.getGroup();
        GroupDto groupDto = new GroupDto();
        groupDto.setGroupId(group.getGroupId());
        groupDto.setGroupName(group.getGroupName());

        GroupMemberDto groupMemberDto = new GroupMemberDto();
        groupMemberDto.setGroupMemberId(id);
        groupMemberDto.setPersonDto(personDto);
        groupMemberDto.setGroupDto(groupDto);

        return groupMemberDto;
    }

    public static GroupMember mapToGroupMember(GroupMemberDto groupMemberDto) {

        GroupMemberId id = groupMemberDto.getGroupMemberId();
        Person person = mapToPerson(groupMemberDto.getPersonDto());
        Group group = mapToGroup(groupMemberDto.getGroupDto());

        GroupMember groupMember = new GroupMember();
        groupMember.setId(id);
        groupMember.setPerson(person);
        groupMember.setGroup(group);

        return groupMember;
    }

}
