package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.GroupMemberDto;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.entity.Group;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.entity.embedabble.GroupMemberId;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.GroupMemberMapper;
import main.loantrackingbackend.mapper.PersonMapper;
import main.loantrackingbackend.repository.GroupMemberRepository;
import main.loantrackingbackend.repository.GroupRepository;
import main.loantrackingbackend.repository.PersonRepository;
import main.loantrackingbackend.service.GroupMemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GroupMemberImpl implements GroupMemberService {

    private PersonRepository personRepository;
    private GroupRepository groupRepository;
    private GroupMemberRepository groupMemberRepository;

    @Override
    public GroupMemberDto addMember(Long groupId, Long personId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Group not found"));

        Person person = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Person not found"));

        GroupMemberId id = new GroupMemberId(groupId, personId);

        if (groupMemberRepository.existsById(id)) {
            throw new IllegalStateException("Person already in group");
        }

        GroupMember member = new GroupMember();
        member.setId(id);
        member.setGroup(group);
        member.setPerson(person);

        GroupMember savedGroupMember = groupMemberRepository.save(member);

        return GroupMemberMapper.mapToGroupMemberDto(savedGroupMember);
    }

    @Override
    public void removeMember(Long groupId, Long personId) {
        GroupMemberId id = new GroupMemberId(groupId, personId);

        GroupMember member = groupMemberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found"));

        groupMemberRepository.delete(member);
    }

    @Override
    public List<PersonDto> getMembersByGroupId(Long groupId) {
        List<GroupMember> groupMembers = groupMemberRepository.findByGroup_GroupId(groupId);


        return groupMembers
                .stream()
                .map((groupMember -> PersonMapper.mapToPersonDto(groupMember.getPerson())))
                .collect(Collectors.toList());
    }
}
