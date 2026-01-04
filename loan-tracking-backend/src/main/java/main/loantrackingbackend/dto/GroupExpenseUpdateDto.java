package main.loantrackingbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupExpenseUpdateDto extends EntryUpdateDto {
    private Long borrowerGroupId;
}