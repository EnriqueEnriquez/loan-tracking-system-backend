package main.loantrackingbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageProofDto {
    private Long id;          // Vital for deletion!
    private String imageUrl;  // Vital for display!
    private String imageName;
}
