package main.loantrackingbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.loantrackingbackend.entity.Payment;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.enums.TransactionType;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EntryResponseDto {
    private UUID id;
    private String entryName;
    private String description;
    private TransactionType transactionType;
    private LocalDate dateBorrowed;
    private LocalDate dateFullyPaid;
    private String borrowerName;
    private String lenderName;
    private BigDecimal amountBorrowed;
    private BigDecimal amountRemaining;
    private PaymentStatus status;
    private String notes;
    private String referenceId;
    private List<ImageProofDto> imageProofs;
    private List<PaymentResponseDto> payments;
}
