package main.loantrackingbackend.dto;

import lombok.Getter;
import lombok.Setter;
import main.loantrackingbackend.enums.PaymentAllocationStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentAllocationResponseDto {
    private Long allocationId;
    private String description;
    private Long groupMemberPersonId;
    private Long borrowerGroupId;
    private PersonDto groupMemberDto;
    private BigDecimal amount;
    private BigDecimal amountPaid;
    private PaymentAllocationStatus paymentAllocationStatus;
    private BigDecimal percent;
    private UUID groupExpenseEntryId;
}