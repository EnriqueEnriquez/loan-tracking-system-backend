package main.loantrackingbackend.dto;

import lombok.Getter;
import lombok.Setter;
import main.loantrackingbackend.enums.InstallmentStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class InstallmentTermResponseDto {
    private Long termId;
    private int termNumber;
    private LocalDate dueDate;
    private InstallmentStatus installmentStatus;
    private UUID installmentExpenseEntryId;
}
