package main.loantrackingbackend.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class InstallmentTermCreateDto {
    private int termNumber;
    private LocalDate dueDate;
    private UUID installmentExpenseEntryId;
}
