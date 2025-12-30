package main.loantrackingbackend.service;

import main.loantrackingbackend.entity.ImageProof;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageProofService {
    ImageProof saveImageFile(MultipartFile imageFile) throws IOException;
}
