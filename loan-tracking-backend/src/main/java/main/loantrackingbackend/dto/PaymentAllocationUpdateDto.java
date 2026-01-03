package main.loantrackingbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentAllocationUpdateDto {
    private String description;
    private Long groupMemberPersonId;
    private BigDecimal amount;
    private BigDecimal percent;
}