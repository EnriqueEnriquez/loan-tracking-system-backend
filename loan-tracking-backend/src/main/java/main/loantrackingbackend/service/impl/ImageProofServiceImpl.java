package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.ImageProof;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.repository.ImageProofRepository;
import main.loantrackingbackend.service.ImageProofService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageProofServiceImpl implements ImageProofService {
    private ImageProofRepository imageProofRepository;

    @Override
    public ImageProof saveImageFile(Entry entry, MultipartFile imageFile) throws IOException {

        String uploadDir = "uploads/";
        Files.createDirectories(Paths.get(uploadDir));

        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path targetPath = Paths.get(uploadDir).resolve(fileName);

        try (InputStream fileContent = imageFile.getInputStream()) {
            Files.copy(fileContent, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        ImageProof imageProof = new ImageProof();
        imageProof.setImageName(fileName);
        imageProof.setImageUrl("/uploads/" + fileName);
        imageProof.setEntry(entry);

        return imageProofRepository.save(imageProof);
    }

    @Override
    public List<ImageProof> saveImageFilesList(Entry entry, List<MultipartFile> imageFiles) throws IOException {
        List<ImageProof> imageProofs = new ArrayList<>();

        if (imageFiles == null || imageFiles.isEmpty()) {
            return imageProofs;
        }

        for (var imageFile : imageFiles) {
            if (imageFile.isEmpty()) {
                continue;
            }

            ImageProof imageProof = saveImageFile(entry, imageFile);
            imageProofs.add(imageProof);
        }

        return imageProofs;
    }

    @Override
    public void deleteImageFile(Long imageId) throws IOException {
        ImageProof imageProof = imageProofRepository.findById(imageId).orElseThrow(
                () -> new ResourceNotFoundException("Image not found")
        );

        Path fileToDeletePath = Paths.get(imageProof.getImageUrl());
        Files.deleteIfExists(fileToDeletePath);

        imageProofRepository.delete(imageProof);

    }
}
