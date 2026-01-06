package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.Person;
import main.loantrackingbackend.entity.StraightExpense;
import main.loantrackingbackend.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StraightExpenseRepository extends JpaRepository<StraightExpense, UUID> {

        boolean existsByPersonBorrower(Person personBorrower);

}
