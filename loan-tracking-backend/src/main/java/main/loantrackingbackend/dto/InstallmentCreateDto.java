package main.loantrackingbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.enums.PaymentFrequency;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstallmentCreateDto extends EntryCreateDto {
    private Long borrowerId;
    private LocalDate startDate;
    private PaymentFrequency paymentFrequency;
    private int paymentTerms;
    private BigDecimal paymentAmountPerTerm;

}
