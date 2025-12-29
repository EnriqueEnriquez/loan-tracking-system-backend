package main.loantrackingbackend.dto;

import main.loantrackingbackend.entity.embedabble.GroupMemberId;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupMemberDto {
    private GroupMemberId groupMemberId;
    private PersonDto personDto;
    private GroupDto groupDto;
}
