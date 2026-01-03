package main.loantrackingbackend.service;

import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.ImageProof;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface ImageProofService {
    ImageProof saveImageFile(Entry entry, MultipartFile imageFile) throws IOException;

    List<ImageProof> saveImageFilesList(Entry entry, List<MultipartFile> imageFiles) throws IOException;

    void deleteImageFile(Long imageId) throws IOException;
}
