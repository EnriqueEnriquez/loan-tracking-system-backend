package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.PaymentAllocationCreateDto;
import main.loantrackingbackend.dto.PaymentAllocationResponseDto;
import main.loantrackingbackend.dto.PaymentResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PaymentAllocationService {
    PaymentAllocationResponseDto createPaymentAllocation(PaymentAllocationCreateDto dto) throws IOException;
    PaymentAllocationResponseDto getPaymentAllocationById(Long paymentAllocationId);
    List<PaymentAllocationResponseDto> getPaymentAllocationsByPayee(Long groupMemberPersonId);
    List<PaymentAllocationResponseDto> getPaymentAllocationsByEntry(UUID entryId);
    List<PaymentAllocationResponseDto> getAllPayments();
    void deleteAllPaymentAllocations();
    void deletePaymentAllocationsByGroupMember(Long groupMemberPersonId);
    void deletePaymentAllocationsByEntry(UUID entryId);
}