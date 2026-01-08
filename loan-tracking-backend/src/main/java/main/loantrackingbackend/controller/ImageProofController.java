package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.PaymentProof;
import main.loantrackingbackend.service.ImageProofService;
import main.loantrackingbackend.service.PaymentProofService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@AllArgsConstructor
@RestController
@RequestMapping("/api/images")
public class ImageProofController {

    private final ImageProofService imageProofService;
    private final PaymentProofService paymentProofService;

    @GetMapping("/entry/{fileName}")
    public ResponseEntity<byte[]> getImageFile(@PathVariable String fileName) throws IOException {
        byte[] imageBytes = imageProofService.getImageFile(fileName);

        // Default to JPEG, or determine based on file extension
        MediaType mediaType = MediaType.IMAGE_JPEG;
        if (fileName.toLowerCase().endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            mediaType = MediaType.APPLICATION_PDF;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageBytes);
    }

    @GetMapping("/payment/{fileName}")
    public ResponseEntity<byte[]> getPaymentProof(@PathVariable String fileName) throws IOException {
        byte[] imageBytes = paymentProofService.getImagePaymentProof(fileName);

        // Default to JPEG, or determine based on file extension
        MediaType mediaType = MediaType.IMAGE_JPEG;
        if (fileName.toLowerCase().endsWith(".png")) {
            mediaType = MediaType.IMAGE_PNG;
        } else if (fileName.toLowerCase().endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        } else if (fileName.toLowerCase().endsWith(".pdf")) {
            mediaType = MediaType.APPLICATION_PDF;
        }

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(imageBytes);
    }
}
