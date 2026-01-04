package main.loantrackingbackend.service.impl;

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

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PaymentAllocationServiceImpl implements PaymentAllocationService {

    private final PaymentAllocationRepository paymentAllocationRepository;
    private final EntryRepository entryRepository;
    private final GroupMemberRepository groupMemberRepository;

    @Override
    public PaymentAllocationResponseDto createPaymentAllocation(PaymentAllocationCreateDto dto) {
        GroupExpense groupExpense = entryRepository.findGEById(dto.getGroupExpenseEntryId());
        GroupMember member = groupMemberRepository.findById(dto.getGroupMemberPersonId());

        PaymentAllocation allocation = PaymentAllocationMapper.mapToPaymentAllocation(dto, groupExpense, member);
        PaymentAllocation savedAllocation = paymentAllocationRepository.save(allocation);

        return PaymentAllocationMapper.mapToPaymentAllocationResponseDto(savedAllocation);
    }

    @Override
    public PaymentAllocationResponseDto getPaymentAllocationByAllocationId(Long allocationId) {
        PaymentAllocation allocation = paymentAllocationRepository.findById(allocationId)
                .orElseThrow(() -> new ResourceNotFoundException("Payment Allocation not found"));

        return PaymentAllocationMapper.mapToPaymentAllocationResponseDto(allocation);
    }

    @Override
    public List<PaymentAllocationResponseDto> getPaymentAllocationByGroupMember(Long groupMemberPersonId) {

        GroupMember member = groupMemberRepository.findById(groupMemberPersonId);

        return PaymentAllocationMapper.mapToResponseList(
                paymentAllocationRepository.findByGroupMember(member)
        );
    }

    @Override
    public List<PaymentAllocationResponseDto> getPaymentAllocationById(UUID id) {
        Entry entry = entryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Entry not found")
                );

        if (!(entry instanceof GroupExpense groupExpense)) {
            throw new IllegalStateException("Entry is not a Group Expense");
        }

        return PaymentAllocationMapper.mapToResponseList(
                paymentAllocationRepository.findByGroupExpense(groupExpense)
        );
    }

    @Override
    public List<PaymentAllocationResponseDto> getAllPaymentAllocations() {
        List<PaymentAllocation> allocations = paymentAllocationRepository.findAll();
        return PaymentAllocationMapper.mapToResponseList(allocations);
    }

    @Override
    public void deleteAllPaymentAllocations() {
        paymentAllocationRepository.deleteAll();
    }

    @Override
    public void deletePaymentAllocationByGroupMember(Long groupMemberPersonId) {
        GroupMember member = groupMemberRepository.findById(groupMemberPersonId);

        paymentAllocationRepository.deleteAll(paymentAllocationRepository.findByGroupMember(member));
    }

    @Override
    public void deletePaymentAllocationById(UUID entryId) {
        Entry entry = entryRepository.findEntryById(entryId);

        if (!(entry instanceof GroupExpense groupExpense)) {
            throw new IllegalStateException("Entry is not a Group Expense");
        }

        paymentAllocationRepository.deleteAll(
                paymentAllocationRepository.findByGroupExpense(groupExpense)
        );
    }
}
