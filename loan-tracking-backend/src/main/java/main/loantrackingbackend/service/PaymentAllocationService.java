package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.PaymentAllocationCreateDto;
import main.loantrackingbackend.dto.PaymentAllocationResponseDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PaymentAllocationService {
    PaymentAllocationResponseDto createPaymentAllocation(PaymentAllocationCreateDto dto) throws IOException;
    PaymentAllocationResponseDto getPaymentAllocationByAllocationId(Long allocationId);
    List<PaymentAllocationResponseDto> getPaymentAllocationByGroupMember(UUID groupExpenseEntryId, Long groupMemberPersonId);
    List<PaymentAllocationResponseDto> getPaymentAllocationById(UUID id);
    List<PaymentAllocationResponseDto> getAllPaymentAllocations();
    void deleteAllPaymentAllocations();
    void deletePaymentAllocationByGroupMember(UUID groupExpenseEntryId, Long groupMemberPersonId);
    void deletePaymentAllocationById(UUID id);
}