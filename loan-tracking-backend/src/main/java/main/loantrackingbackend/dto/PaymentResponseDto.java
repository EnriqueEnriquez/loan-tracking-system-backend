package main.loantrackingbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
    private Long paymentId;
    private LocalDate paymentDate;
    private BigDecimal paymentAmount;
    private PersonDto payeeDto;
    private String notes;
    private List<String> imageUrls;
    private UUID entryId;
    private Long termId;    // to track term iff installment expense
}