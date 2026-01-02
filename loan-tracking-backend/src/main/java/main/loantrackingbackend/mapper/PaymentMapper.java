package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.PaymentCreateDto;
import main.loantrackingbackend.dto.PaymentResponseDto;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.PaymentProof;
import main.loantrackingbackend.entity.Payment;

import java.util.stream.Collectors;

import static main.loantrackingbackend.mapper.PersonMapper.mapToPersonDto;

public class PaymentMapper {

    public static PaymentResponseDto mapToPaymentResponseDto(Payment payment) {
        if (payment == null) {
            return null;
        }

        PaymentResponseDto dto = new PaymentResponseDto();

        dto.setPaymentId(payment.getPaymentId());
        dto.setPaymentDate(payment.getPaymentDate());
        dto.setPaymentAmount(payment.getPaymentAmount());
        dto.setNotes(payment.getNotes());

        if (payment.getPayee() != null) {
            dto.setPayeeDto(mapToPersonDto(payment.getPayee()));
        }

        if (payment.getImageFiles() != null) {
            dto.setImageUrls(
                    payment.getImageFiles()
                            .stream()
                            .map(PaymentProof::getImageUrl)
                            .collect(Collectors.toList())
            );
        }

        dto.setEntryId(payment.getEntry().getId());

        return dto;
    }

    public static Payment mapToPayment(PaymentCreateDto dto) {
        Payment payment = new Payment();

        payment.setPaymentAmount(dto.getPaymentAmount());
        payment.setNotes(dto.getNotes());

        return payment;
    }
}