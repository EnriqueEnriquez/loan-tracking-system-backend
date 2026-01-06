package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.GroupDto;
import main.loantrackingbackend.entity.Group;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.exception.DuplicateResourceException;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.GroupMapper;
import main.loantrackingbackend.repository.GroupExpenseRepository;
import main.loantrackingbackend.repository.GroupMemberRepository;
import main.loantrackingbackend.repository.GroupRepository;
import main.loantrackingbackend.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupExpenseRepository groupExpenseRepository;
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;

    @Override
    public GroupDto createGroup(GroupDto groupDto) {
        if (groupRepository.existsByGroupName(groupDto.getGroupName())) {
            throw new DuplicateResourceException("Group with name " + groupDto.getGroupName() + " already exists");
        }

        Group group = GroupMapper.mapToGroup(groupDto);

        Group saveGroup = groupRepository.save(group);

        return GroupMapper.mapToGroupDto(saveGroup);
    }

    @Override
    public GroupDto getGroupById(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group does not exist with id: " + groupId));

        return GroupMapper.mapToGroupDto(group);
    }

    @Override
    public List<GroupDto> getAllGroups() {
        List<Group> groups = groupRepository.findAll();

        return groups.stream().map((group) -> GroupMapper.mapToGroupDto(group)).collect(Collectors.toList());
    }

    @Override
    public GroupDto updateGroup(Long groupId, GroupDto groupDto) {
        if (groupRepository.existsByGroupName(groupDto.getGroupName())) {
            throw new DuplicateResourceException("Group with name " + groupDto.getGroupName() + " already exists");
        }

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group does not exist with id: " + groupId));

        group.setGroupName(groupDto.getGroupName());

        Group savedGroup = groupRepository.save(group);

        return GroupMapper.mapToGroupDto(savedGroup);
    }

    @Override
    public String deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group does not exist with id: " + groupId));

        if (groupExpenseRepository.existsByGroupBorrower(group)) {
            return "Cannot delete Group " + group.getGroupName() + " with group id: " + groupId +
                    "because it is associated with expense/s. Delete those expense first.";
        }

        groupRepository.delete(group);
        return "Group " + group.getGroupName() + "with group id: " + groupId + " has been deleted";
    }

    @Override
    public long countMembers(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new ResourceNotFoundException("Group not found with id: " + groupId);
        }

        return groupMemberRepository.countByGroup_GroupId(groupId);
    }
}
