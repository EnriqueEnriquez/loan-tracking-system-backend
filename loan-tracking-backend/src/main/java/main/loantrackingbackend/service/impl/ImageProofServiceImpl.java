package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.ImageProof;
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
import java.util.UUID;

@Service
@AllArgsConstructor
public class ImageProofServiceImpl implements ImageProofService {
    private ImageProofRepository imageProofRepository;

    @Override
    public ImageProof saveImageFile(MultipartFile imageFile) throws IOException {

        String folderDir = "uploads/";
        Files.createDirectories(Paths.get(folderDir));
        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path targetPath = Paths.get(folderDir, fileName);

        try (InputStream fileContent = imageFile.getInputStream()) {
            Files.copy(fileContent, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        ImageProof imageProof = new ImageProof();
        imageProof.setImageName(fileName);
        imageProof.setImageUrl(targetPath.toString());

        return imageProofRepository.save(imageProof);
    }
}
