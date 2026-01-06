package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.GroupMemberDto;
import main.loantrackingbackend.dto.PersonDto;
import main.loantrackingbackend.service.GroupMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/groups/{groupId}/members")
@AllArgsConstructor
public class GroupMemberController {
    private GroupMemberService groupMemberService;

    @PostMapping("/{personId}")
    public ResponseEntity<?> addMember(@PathVariable Long groupId, @PathVariable Long personId) {
        try {
            GroupMemberDto groupMemberDto = groupMemberService.addMember(groupId, personId);
            return new ResponseEntity<>(groupMemberDto, HttpStatus.CREATED);
        } catch (Exception e) {
            Map<String, String> response= Collections.singletonMap("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<PersonDto>> getAllMembersByGroupId(@PathVariable Long groupId) {
        List<PersonDto> groupMembers = groupMemberService.getMembersByGroupId(groupId);

        return new ResponseEntity<>(groupMembers, HttpStatus.OK);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Map<String, String>> deleteGroupMember(@PathVariable Long groupId, @PathVariable Long personId) {
        String resultMessage = groupMemberService.removeMember(groupId, personId);
        Map<String, String> response = Collections.singletonMap("message", resultMessage);

        if (resultMessage.startsWith("Cannot")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(response);
    }

}
