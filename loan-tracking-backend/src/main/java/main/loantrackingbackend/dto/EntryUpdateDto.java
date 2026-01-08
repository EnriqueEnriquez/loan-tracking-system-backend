package main.loantrackingbackend.dto;


import lombok.Data;
import main.loantrackingbackend.enums.PaymentFrequency;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
public class EntryUpdateDto {
    private String entryName;
    private String description;
    private LocalDate dateBorrowed;
    private Long lenderId;
    private BigDecimal amountBorrowed;
    private String notes;
    private List<MultipartFile> imageFiles;
    private List<Long> deletedImageIds;

    private Long personBorrowerId; // Used for Straight/Installment

    // For installment
    private LocalDate startDate;
    private PaymentFrequency paymentFrequency;
    private int paymentTerms;
    private BigDecimal paymentAmountPerTerm;

    // For group expense only
    private Long groupBorrowerId;
    private List<PaymentAllocationCreateDto> paymentAllocations;

}

