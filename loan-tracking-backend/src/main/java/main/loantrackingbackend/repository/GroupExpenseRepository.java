package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Group;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.GroupMember;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface GroupExpenseRepository extends JpaRepository<GroupExpense, UUID> {


    @Query("""
        SELECT COUNT(ge) > 0\s
        FROM GroupExpense ge
        JOIN ge.groupBorrower groupBorrower
        JOIN groupBorrower.groupMembers member
        WHERE member = :person
   \s""")
    boolean existsByGroupMember(
            @Param("person") GroupMember groupMemberBorrower
    );

    boolean existsByGroupBorrower(Group groupBorrower);
}
