package main.loantrackingbackend.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = StraightUpdateDto.class, name = "STRAIGHT"),
        @JsonSubTypes.Type(value = InstallmentUpdateDto.class, name = "INSTALLMENT")
})
@Data
public class EntryUpdateDto {
    private String entryName;
    private String description;
    private LocalDate dateBorrowed;
    private Long lenderId;
    private String notes;
    private List<MultipartFile> imageFiles;

}

