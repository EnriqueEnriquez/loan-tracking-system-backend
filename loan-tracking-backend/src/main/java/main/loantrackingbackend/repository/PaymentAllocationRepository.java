package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.PaymentAllocation;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentAllocationRepository extends JpaRepository<PaymentAllocation, Long> {
    List<PaymentAllocation> findByGroupExpense(GroupExpense groupExpense);
    List<PaymentAllocation> findByGroupMember(GroupMember groupMember);

    @Query("""
        SELECT COUNT(pa) > 0\s
        FROM PaymentAllocation pa
        JOIN pa.groupMember gm
        JOIN pa.groupExpense ge
        WHERE gm.person = :person
        AND ge.status IN :statuses
   \s""")
    boolean existsByPersonAndGroupExpenseStatus(
            @Param("person") Person personBorrower,
            @Param("statuses") List<PaymentStatus> statuses
    );
}
