package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.GroupMemberDto;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.service.GroupMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups/{groupId}/members")
@AllArgsConstructor
public class GroupMemberController {
    private GroupMemberService groupMemberService;

    @PostMapping("/{personId}")
    public ResponseEntity<GroupMemberDto> addMember(@PathVariable Long groupId, @PathVariable Long personId) {
        GroupMemberDto groupMemberDto = groupMemberService.addMember(groupId, personId);

        return new ResponseEntity<>(groupMemberDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllMembersByGroupId(@PathVariable Long groupId) {
        List<PersonDto> groupMembers = groupMemberService.getMembersByGroupId(groupId);

        return new ResponseEntity<>(groupMembers, HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<String> deleteGroupMember(@PathVariable Long groupId, @PathVariable Long personId) {
        groupMemberService.removeMember(groupId, personId);
        return ResponseEntity.ok("Member removed from group");
    }

}
