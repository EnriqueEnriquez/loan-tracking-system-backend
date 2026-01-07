package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.ImageProof;
import main.loantrackingbackend.entity.Payment;
import main.loantrackingbackend.entity.PaymentProof;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.repository.PaymentProofRepository;
import main.loantrackingbackend.service.PaymentProofService;
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
public class PaymentProofServiceImpl implements PaymentProofService {

    private final PaymentProofRepository paymentProofRepository;

    @Override
    public PaymentProof savePaymentProof(Payment payment, MultipartFile imageFile) throws IOException {

        String folderDir = "uploads/payments/";
        Files.createDirectories(Paths.get(folderDir));

        String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
        Path targetPath = Paths.get(folderDir, fileName);

        try (InputStream fileContent = imageFile.getInputStream()) {
            Files.copy(fileContent, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

        PaymentProof paymentProof = new PaymentProof();
        paymentProof.setImageName(fileName);
        paymentProof.setImageUrl(targetPath.toString());
        paymentProof.setPayment(payment);

        return paymentProofRepository.save(paymentProof);
    }

    @Override
    public byte[] getImagePaymentProof(String imageName) throws IOException {
        PaymentProof paymentProof = paymentProofRepository.findByImageName(imageName);
        String filePath = paymentProof.getImageUrl();
        return Files.readAllBytes(Paths.get(filePath));
    }

    @Override
    public void deletePaymentProof(Long proofId) throws IOException {

        PaymentProof proof = paymentProofRepository.findById(proofId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment proof not found"));

        paymentProofRepository.delete(proof);
    }
}