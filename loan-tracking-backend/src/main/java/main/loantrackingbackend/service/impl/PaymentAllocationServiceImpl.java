package main.loantrackingbackend.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PaymentAllocationCreateDto;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.PaymentAllocation;
import main.loantrackingbackend.entity.embedabble.GroupMemberId;
import main.loantrackingbackend.enums.PaymentAllocationStatus;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.repository.GroupMemberRepository;
import main.loantrackingbackend.repository.PaymentAllocationRepository;
import main.loantrackingbackend.service.PaymentAllocationService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentAllocationServiceImpl implements PaymentAllocationService {

    private final GroupMemberRepository groupMemberRepository;
    private final PaymentAllocationRepository allocationRepository;

    private void assertAllocationsEditable(GroupExpense expense) {
        boolean locked = expense.getPaymentAllocations().stream()
                .anyMatch(a -> !a.isEditable());
        if (locked) {
            throw new IllegalStateException("Cannot modify allocations once payments have started.");
        }
    }

    private void computePercent(PaymentAllocation allocation, BigDecimal totalAmount) {
        if (totalAmount.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal percent = allocation.getAmount()
                    .multiply(BigDecimal.valueOf(100))
                    .divide(totalAmount, 2, RoundingMode.HALF_UP);
            allocation.setPercent(percent);
        } else {
            allocation.setPercent(BigDecimal.ZERO);
        }
    }

    public void divideEqually(GroupExpense expense) {
        assertAllocationsEditable(expense);

        List<GroupMember> members = groupMemberRepository.findByGroup_GroupId(
                expense.getGroupBorrower().getGroupId()
        );

        if (members.isEmpty()) {
            throw new IllegalStateException("Group has no members.");
        }

        BigDecimal total = expense.getAmountBorrowed().setScale(2, RoundingMode.HALF_UP);
        int count = members.size();
        BigDecimal amountPerMember = total.divide(BigDecimal.valueOf(count), 2, RoundingMode.UP);

        expense.getPaymentAllocations().clear();

        for (GroupMember member : members) {
            PaymentAllocation allocation = new PaymentAllocation();
            allocation.setGroupExpense(expense);
            allocation.setGroupMember(member);
            allocation.setAmount(amountPerMember);
            allocation.setPaymentAllocationStatus(PaymentAllocationStatus.UNPAID);

            computePercent(allocation, total);

            expense.getPaymentAllocations().add(allocation);
        }

        allocationRepository.saveAll(expense.getPaymentAllocations());
    }

    public void divideByPercent(GroupExpense expense, List<PaymentAllocationCreateDto> dtos) {
        assertAllocationsEditable(expense);

        BigDecimal totalPercent = dtos.stream()
                .map(PaymentAllocationCreateDto::getPercent)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalPercent.compareTo(BigDecimal.valueOf(100)) != 0) {
            throw new IllegalArgumentException("Percent total must equal 100");
        }

        BigDecimal totalAmount = expense.getAmountBorrowed();
        expense.getPaymentAllocations().clear();

        for (PaymentAllocationCreateDto dto : dtos) {
            GroupMember member = groupMemberRepository.findById(
                    new GroupMemberId(dto.getGroupMemberPersonId(), expense.getGroupBorrower().getGroupId())
            ).orElseThrow(() -> new ResourceNotFoundException("Group member not found"));

            PaymentAllocation allocation = new PaymentAllocation();
            allocation.setGroupExpense(expense);
            allocation.setGroupMember(member);
            allocation.setPercent(dto.getPercent());

            BigDecimal amount = totalAmount.multiply(dto.getPercent())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            allocation.setAmount(amount);
            allocation.setPaymentAllocationStatus(PaymentAllocationStatus.UNPAID);

            expense.getPaymentAllocations().add(allocation);
        }

        allocationRepository.saveAll(expense.getPaymentAllocations());
    }

    public void divideByAmount(GroupExpense expense, List<PaymentAllocationCreateDto> dtos) {
        assertAllocationsEditable(expense);

        BigDecimal totalAmount = expense.getAmountBorrowed();

        BigDecimal sum = dtos.stream()
                .map(PaymentAllocationCreateDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (sum.compareTo(totalAmount) != 0) {
            throw new IllegalArgumentException("Sum of allocation amounts must equal total expense");
        }

        expense.getPaymentAllocations().clear();

        for (PaymentAllocationCreateDto dto : dtos) {
            GroupMember member = groupMemberRepository.findById(
                    new GroupMemberId(dto.getGroupMemberPersonId(), expense.getGroupBorrower().getGroupId())
            ).orElseThrow(() -> new ResourceNotFoundException("Group member not found"));

            PaymentAllocation allocation = new PaymentAllocation();
            allocation.setGroupExpense(expense);
            allocation.setGroupMember(member);
            allocation.setAmount(dto.getAmount());
            allocation.setPaymentAllocationStatus(PaymentAllocationStatus.UNPAID);

            // compute percent automatically
            computePercent(allocation, totalAmount);

            expense.getPaymentAllocations().add(allocation);
        }

        allocationRepository.saveAll(expense.getPaymentAllocations());
    }
}