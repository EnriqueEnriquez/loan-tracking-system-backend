package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.PaymentCreateDto;
import main.loantrackingbackend.dto.PaymentResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentCreateDto dto) throws IOException;
    PaymentResponseDto getPaymentById(Long paymentId);
    List<PaymentResponseDto> getPaymentsByPayee(Long payeeId);
    List<PaymentResponseDto> getPaymentsByEntry(UUID entryId);
    List<PaymentResponseDto> getAllPayments();
    void deleteAllPayments();
    void deletePaymentsByPayee(Long payeeId);
    void deletePaymentsByEntry(UUID entryId);
}
