package main.loantrackingbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class InstallmentUpdateDto extends EntryUpdateDto {
    // specific fields that are only editable in "Draft Mode"
    private Long personBorrowedId;
    private LocalDate startDate;

}
