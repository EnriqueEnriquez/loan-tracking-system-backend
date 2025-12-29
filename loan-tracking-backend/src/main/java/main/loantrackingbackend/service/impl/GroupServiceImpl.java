package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.GroupDto;
import main.loantrackingbackend.entity.Group;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.GroupMapper;
import main.loantrackingbackend.repository.GroupRepository;
import main.loantrackingbackend.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;

    @Override
    public GroupDto createGroup(GroupDto groupDto) {
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
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group does not exist with id: " + groupId));

        group.setGroupName(groupDto.getGroupName());

        Group savedGroup = groupRepository.save(group);

        return GroupMapper.mapToGroupDto(savedGroup);
    }

    @Override
    public void deleteGroup(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group does not exist with id: " + groupId));

        groupRepository.delete(group);
    }
}
