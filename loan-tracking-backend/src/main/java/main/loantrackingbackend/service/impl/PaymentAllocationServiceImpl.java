package main.loantrackingbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PaymentAllocationCreateDto;
import main.loantrackingbackend.dto.PaymentAllocationResponseDto;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.PaymentAllocation;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.mapper.PaymentAllocationMapper;
import main.loantrackingbackend.repository.EntryRepository;
import main.loantrackingbackend.repository.GroupMemberRepository;
import main.loantrackingbackend.repository.PaymentAllocationRepository;
import main.loantrackingbackend.service.PaymentAllocationService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
/**
@Service
@AllArgsConstructor
@Transactional
public class PaymentAllocationServiceImpl implements PaymentAllocationService {

    private final PaymentAllocationRepository paymentAllocationRepository;
    private final EntryRepository entryRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Override
    public PaymentAllocationResponseDto createPaymentAllocation(PaymentAllocationCreateDto dto) throws IOException {
        GroupExpense groupExpense = (GroupExpense) entryRepository.findById(dto.getEntryId())
                .orElseThrow(() -> new ResourceNotFoundException("Entry not found"));

        GroupMember member = (GroupMember) groupMemberRepository.findById(dto.getGroupMemberPersonId());

        PaymentAllocation allocation = PaymentAllocationMapper.mapToPaymentAllocation(dto, groupExpense, member);
        PaymentAllocation savedAllocation = paymentAllocationRepository.save(allocation);

        return PaymentAllocationMapper.mapToPaymentAllocationResponseDto(savedAllocation);
    }

    @Override
    public PaymentAllocationResponseDto getPaymentAllocationById(Long paymentAllocationId) {
        PaymentAllocation allocation = paymentAllocationRepository.findById(paymentAllocationId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment Allocation not found"));

        return PaymentAllocationMapper.mapToPaymentAllocationResponseDto(allocation);
    }

    @Override
    public List<PaymentAllocationResponseDto> getPaymentAllocationsByPayee(Long groupMemberPersonId) {
        List<PaymentAllocation> allocations = paymentAllocationRepository.findByGroupMember_PersonId(groupMemberPersonId);
        return PaymentAllocationMapper.mapToResponseList(allocations);
    }

    @Override
    public List<PaymentAllocationResponseDto> getPaymentAllocationsByEntry(UUID entryId) {
        List<PaymentAllocation> allocations = paymentAllocationRepository.findByGroupExpense_Id(entryId);
        return PaymentAllocationMapper.mapToResponseList(allocations);
    }

    @Override
    public List<PaymentAllocationResponseDto> getAllPayments() {
        List<PaymentAllocation> allocations = paymentAllocationRepository.findAll();
        return PaymentAllocationMapper.mapToResponseList(allocations);
    }

    @Override
    public void deleteAllPaymentAllocations() {
        paymentAllocationRepository.deleteAll();
    }

    @Override
    public void deletePaymentAllocationsByGroupMember(Long groupMemberPersonId) {
        List<PaymentAllocation> allocations = paymentAllocationRepository.findByGroupMember_PersonId(groupMemberPersonId);
        paymentAllocationRepository.deleteAll(allocations);
    }

    @Override
    public void deletePaymentAllocationsByEntry(UUID entryId) {
        List<PaymentAllocation> allocations = paymentAllocationRepository.findByGroupExpense_Id(entryId);
        paymentAllocationRepository.deleteAll(allocations);
    }
}*/
