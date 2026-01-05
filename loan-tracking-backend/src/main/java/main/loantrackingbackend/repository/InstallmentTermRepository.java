package main.loantrackingbackend.repository;

import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.InstallmentTerm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InstallmentTermRepository extends JpaRepository<InstallmentTerm, Long> {

    void deleteByInstallmentExpense(InstallmentExpense installmentExpense);
}
