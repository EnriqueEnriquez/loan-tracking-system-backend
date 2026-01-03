package main.loantrackingbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentAllocationResponseDto {
    private Long allocationId;
    private String description;
    private Long groupMemberPersonId;
    private Long borrowerGroupId;
    private PersonDto payeeDto;
    private BigDecimal amount;
    private BigDecimal percent;
    private UUID entryId;
}