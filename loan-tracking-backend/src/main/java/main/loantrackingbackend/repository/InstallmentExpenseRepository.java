package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface InstallmentExpenseRepository extends JpaRepository<InstallmentExpense, UUID> {

    boolean existsByPersonBorrowerAndStatusIn(Person personBorrower, List<PaymentStatus> statuses);

}
