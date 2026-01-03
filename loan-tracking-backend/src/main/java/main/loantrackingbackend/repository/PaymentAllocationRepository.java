package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.PaymentAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentAllocationRepository extends JpaRepository<PaymentAllocation, Long> {
    //List<PaymentAllocation> findByGroupExpense_Id(UUID entryId);
    //List<PaymentAllocation> findByGroupMember_PersonId(Long personId);
    //void deleteByGroupExpense_Id(UUID entryId);
    //void deleteByGroupMember_PersonId(Long personId);
}
