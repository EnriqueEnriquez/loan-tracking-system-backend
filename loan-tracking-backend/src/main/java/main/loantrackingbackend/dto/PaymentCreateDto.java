package main.loantrackingbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentCreateDto {
    private BigDecimal paymentAmount;
    private Long personId;
    private String notes;
    private List<MultipartFile> imageFiles;
    private UUID entryId;
}
