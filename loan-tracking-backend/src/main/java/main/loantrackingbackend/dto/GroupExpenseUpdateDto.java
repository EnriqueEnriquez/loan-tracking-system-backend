package main.loantrackingbackend.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GroupExpenseUpdateDto extends EntryUpdateDto {
    private Long borrowerGroupId;
    private List<PaymentAllocationCreateDto> paymentAllocations;
}