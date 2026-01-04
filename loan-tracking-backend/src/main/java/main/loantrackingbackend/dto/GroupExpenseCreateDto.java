package main.loantrackingbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupExpenseCreateDto extends EntryCreateDto {
    private Long borrowerGroupId;
}