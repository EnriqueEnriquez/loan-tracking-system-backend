package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.PaymentAllocationCreateDto;
import main.loantrackingbackend.entity.GroupExpense;

import java.util.List;

public interface PaymentAllocationService {
    void divideEqually(GroupExpense expense);
    void divideByPercent(GroupExpense expense, List<PaymentAllocationCreateDto> dtos);
    void divideByAmount(GroupExpense expense, List<PaymentAllocationCreateDto> dtos);
}