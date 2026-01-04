package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.PaymentAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentAllocationRepository extends JpaRepository<PaymentAllocation, Long> {
    List<PaymentAllocation> findByGroupExpense(GroupExpense groupExpense);
    List<PaymentAllocation> findByGroupMember(GroupMember groupMember);
}
