package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.GroupDto;
import main.loantrackingbackend.service.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/groups")
public class GroupController {
    private GroupService groupService;

    @PostMapping
    public ResponseEntity<GroupDto> createGroup(@RequestBody GroupDto groupDto) {
        GroupDto savedGroup = groupService.createGroup(groupDto);

        return new ResponseEntity<>(savedGroup, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<GroupDto> getGroupById(@PathVariable("id") Long groupId) {
        GroupDto groupDto = groupService.getGroupById(groupId);

        return new ResponseEntity<>(groupDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GroupDto>> getAllGroups() {
        List<GroupDto> groups = groupService.getAllGroups();

        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<GroupDto> updateGroup(@PathVariable("id") Long groupId, @RequestBody GroupDto updatedGroup) {

        GroupDto groupDto = groupService.updateGroup(groupId, updatedGroup);

        return new ResponseEntity<>(groupDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Map<String, String>> deleteGroup(@PathVariable("id") Long groupId) {
        String resultMessage  = groupService.deleteGroup(groupId);

        Map<String, String> response = Collections.singletonMap("message", resultMessage);

        if (resultMessage.startsWith("Cannot")) {
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
