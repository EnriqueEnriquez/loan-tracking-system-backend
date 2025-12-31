package main.loantrackingbackend.service;

import main.loantrackingbackend.entity.ImageProof;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

public interface ImageProofService {
    ImageProof saveImageFile(MultipartFile imageFile) throws IOException;

    void deleteImageFile(Long imageId) throws IOException;
}
