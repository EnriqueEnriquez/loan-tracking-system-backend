package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PaymentCreateDto;
import main.loantrackingbackend.dto.PaymentResponseDto;
import main.loantrackingbackend.entity.PaymentProof;
import main.loantrackingbackend.entity.Payment;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.PaymentMapper;
import main.loantrackingbackend.repository.PaymentRepository;
import main.loantrackingbackend.repository.PersonRepository;
import main.loantrackingbackend.service.PaymentProofService;
import main.loantrackingbackend.service.PaymentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PersonRepository personRepository;
    private final PaymentProofService paymentProofService;

    @Override
    public PaymentResponseDto createPayment(PaymentCreateDto dto) throws IOException {

        Person payee = personRepository.findById(dto.getPayeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Payee not found"));

        Payment payment = PaymentMapper.mapToPayment(dto);
        payment.setPayee(payee);
        payment.setPaymentDate(LocalDate.now());

        payment = paymentRepository.save(payment);

        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            for (var file : dto.getImageFiles()) {
                PaymentProof proof = paymentProofService.savePaymentProof(payment, file);
                payment.getImageFiles().add(proof);
            }
        }

        payment = paymentRepository.save(payment);

        return PaymentMapper.mapToPaymentResponseDto(payment);
    }

    @Override
    public PaymentResponseDto getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found"));

        return PaymentMapper.mapToPaymentResponseDto(payment);
    }

    @Override
    public List<PaymentResponseDto> getPaymentsByPayee(Long personId) {

        Person payee = personRepository.findById(personId)
                .orElseThrow(() -> new ResourceNotFoundException("Payee not found"));

        List<Payment> payments = paymentRepository.findByPayee(payee);

        return payments.stream()
                .map(PaymentMapper::mapToPaymentResponseDto)
                .toList();
    }

    @Override
    public List<PaymentResponseDto> getAllPayments() {

        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::mapToPaymentResponseDto)
                .toList();
    }
}
