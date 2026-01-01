package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.PaymentCreateDto;
import main.loantrackingbackend.dto.PaymentResponseDto;

import java.io.IOException;
import java.util.List;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentCreateDto dto) throws IOException;
    PaymentResponseDto getPaymentById(Long paymentId);
    List<PaymentResponseDto> getPaymentsByPayee(Long payeeId);
    List<PaymentResponseDto> getAllPayments();
}
