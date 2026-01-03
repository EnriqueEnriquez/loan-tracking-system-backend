package main.loantrackingbackend.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
public class EntryCreateDto {
    private UUID id;
    private String entryName;
    private String description;
    private TransactionType transactionType;
    private LocalDate dateBorrowed;
    private LocalDate dateFullyPaid;
    private Long lenderId;
    private BigDecimal amountBorrowed;
    private BigDecimal amountRemaining;
    private String notes;
    private List<MultipartFile> imageFiles;
}