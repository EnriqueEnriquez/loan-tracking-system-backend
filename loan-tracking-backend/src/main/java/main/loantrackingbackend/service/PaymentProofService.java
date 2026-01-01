package main.loantrackingbackend.service;

import main.loantrackingbackend.entity.Payment;
import main.loantrackingbackend.entity.PaymentProof;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PaymentProofService {

    PaymentProof savePaymentProof(Payment payment, MultipartFile imageFile) throws IOException;

    void deletePaymentProof(Long proofId) throws IOException;
}