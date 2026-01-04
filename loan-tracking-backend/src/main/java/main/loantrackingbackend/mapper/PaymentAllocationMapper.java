package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.PaymentAllocationCreateDto;
import main.loantrackingbackend.dto.PaymentAllocationResponseDto;
import main.loantrackingbackend.dto.PaymentAllocationUpdateDto;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.PaymentAllocation;

import java.util.List;
import java.util.stream.Collectors;

public class PaymentAllocationMapper {

    public static PaymentAllocationResponseDto mapToPaymentAllocationResponseDto(PaymentAllocation allocation) {
        if (allocation == null) {
            return null;
        }

        PaymentAllocationResponseDto dto = new PaymentAllocationResponseDto();

        dto.setAllocationId(allocation.getAllocationId());
        dto.setGroupExpenseEntryId(allocation.getGroupExpense() != null ? allocation.getGroupExpense().getId() : null);
        dto.setGroupMemberPersonId(allocation.getGroupMember() != null ? allocation.getGroupMember().getPerson().getPersonId() : null);
        dto.setBorrowerGroupId(allocation.getGroupExpense() != null ? allocation.getGroupExpense().getGroupBorrower().getGroupId() : null);
        dto.setAmount(allocation.getAmount());
        dto.setDescription(allocation.getDescription());
        dto.setPercent(allocation.getPercent());
        dto.setAmountPaid(allocation.getAmountPaid());
        dto.setPaymentAllocationStatus(allocation.getPaymentAllocationStatus());

        assert allocation.getGroupMember() != null;
        dto.setGroupMemberDto(GroupMemberMapper.mapToGroupMemberDto(allocation.getGroupMember()).getPersonDto());

        return dto;
    }

    public static PaymentAllocation mapToPaymentAllocation(PaymentAllocationCreateDto dto, GroupExpense groupExpense, GroupMember member) {
        if (dto == null || groupExpense == null || member == null) {
            return null;
        }

        PaymentAllocation allocation = new PaymentAllocation();

        allocation.setGroupExpense(groupExpense);
        allocation.setGroupMember(member);
        allocation.setAmount(dto.getAmount());
        allocation.setDescription(dto.getDescription());
        allocation.setPercent(dto.getPercent());

        return allocation;
    }

    public static void mapToPaymentAllocation(PaymentAllocation allocation, PaymentAllocationUpdateDto updateDto) {
        if (allocation == null || updateDto == null) {
            return;
        }

        if (updateDto.getAmount() != null) {
            allocation.setAmount(updateDto.getAmount());
        }

        if (updateDto.getDescription() != null) {
            allocation.setDescription(updateDto.getDescription());
        }

        if (updateDto.getPercent() != null) {
            allocation.setPercent(updateDto.getPercent());
        }
    }

    public static List<PaymentAllocationResponseDto> mapToResponseList(List<PaymentAllocation> allocations) {
        return allocations.stream()
                .map(PaymentAllocationMapper::mapToPaymentAllocationResponseDto)
                .collect(Collectors.toList());
    }
}