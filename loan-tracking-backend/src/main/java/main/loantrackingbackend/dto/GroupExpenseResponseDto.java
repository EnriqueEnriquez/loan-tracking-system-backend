package main.loantrackingbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupExpenseResponseDto extends EntryResponseDto {
    private Long borrowerGroupId;
    private List<PaymentAllocationResponseDto> paymentAllocations;
}