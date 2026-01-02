package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PaymentCreateDto;
import main.loantrackingbackend.dto.PaymentResponseDto;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.PaymentProof;
import main.loantrackingbackend.entity.Payment;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.PaymentMapper;
import main.loantrackingbackend.repository.EntryRepository;
import main.loantrackingbackend.repository.PaymentRepository;
import main.loantrackingbackend.repository.PersonRepository;
import main.loantrackingbackend.service.PaymentProofService;
import main.loantrackingbackend.service.PaymentService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PersonRepository personRepository;
    private final PaymentProofService paymentProofService;
    private final EntryRepository entryRepository;

    @Override
    public PaymentResponseDto createPayment(PaymentCreateDto dto) throws IOException {

        Person payee = personRepository.findById(dto.getPersonId())
                .orElseThrow(() -> new ResourceNotFoundException("Payee not found"));

        Payment payment = PaymentMapper.mapToPayment(dto);
        payment.setPayee(payee);

        payment.setPaymentDate(LocalDate.now());

        Entry entry = entryRepository.findById(dto.getEntryId())
                .orElseThrow(() -> new ResourceNotFoundException("Entry not found"));

        payment.setEntry(entry);

        payment = paymentRepository.save(payment);

        if (dto.getImageFiles() != null && !dto.getImageFiles().isEmpty()) {
            for (var file : dto.getImageFiles()) {
                PaymentProof proof = paymentProofService.savePaymentProof(payment, file);
                payment.getImageFiles().add(proof);
            }
        }

        payment = paymentRepository.save(payment);
        updatePaymentStatus(entry);

        return PaymentMapper.mapToPaymentResponseDto(payment);
    }


    public void updatePaymentStatus(Entry entry) {
        BigDecimal totalPaid = BigDecimal.ZERO;

        for (Payment payment : entry.getPayments()) {
            BigDecimal paymentAmount = payment.getPaymentAmount();
            if (paymentAmount != null) {
                totalPaid = totalPaid.add(paymentAmount);
            }
        }

        BigDecimal amountBorrowed = entry.getAmountBorrowed() != null ? entry.getAmountBorrowed() : BigDecimal.ZERO;
        BigDecimal amountRemaining = amountBorrowed.subtract(totalPaid);

        entry.setAmountRemaining(amountRemaining.max(BigDecimal.ZERO));

        if (totalPaid.compareTo(BigDecimal.ZERO) == 0) {
            entry.setStatus(PaymentStatus.UNPAID);
        } else if (totalPaid.compareTo(amountBorrowed) < 0) {
            entry.setStatus(PaymentStatus.PARTIALLY_PAID);
        } else {
            entry.setStatus(PaymentStatus.PAID);
            entry.setDateFullyPaid(LocalDate.now());
        }

        entryRepository.save(entry);
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
    public List<PaymentResponseDto> getPaymentsByEntry(UUID entryId) {

        Entry entry = entryRepository.findById(entryId)
                .orElseThrow(() -> new ResourceNotFoundException("Entry not found"));

        List<Payment> payments = paymentRepository.findByEntry(entry);

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

    @Override
    public void deleteAllPayments() {
        paymentRepository.deleteAll();
    }

    @Override
    public void deletePaymentsByPayee(Long payeeId) {
        paymentRepository.deleteByPayeePersonId(payeeId);
    }

    @Override
    public void deletePaymentsByEntry(UUID entryId) {
        paymentRepository.deleteByEntryId(entryId);
    }

}
