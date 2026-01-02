package main.loantrackingbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StraightUpdateDto extends EntryUpdateDto {
    private Long personBorrowedId;
}

