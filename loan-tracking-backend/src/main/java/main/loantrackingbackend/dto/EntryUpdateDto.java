package main.loantrackingbackend.dto;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.time.LocalDate;
import java.util.List;


@Data
public class EntryUpdateDto {
    private String entryName;
    private String description;
    private LocalDate dateBorrowed;
    private Long lenderId;
    private String notes;

    private Long personBorrowedId; // Used for Straight/Installment
    private Long borrowerGroupId;

    private List<MultipartFile> imageFiles;
    private List<Long> deletedImageIds;

}

