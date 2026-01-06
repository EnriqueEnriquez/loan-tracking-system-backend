package main.loantrackingbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class PaymentAllocationUpdateDto {
    private String description;
    private Long groupMemberPersonId;
    private BigDecimal amount;
    private BigDecimal percent;
    private UUID groupExpenseEntryId;
    private String notes;
}