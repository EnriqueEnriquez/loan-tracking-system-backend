package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.InstallmentTerm;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.repository.InstallmentTermRepository;
import main.loantrackingbackend.service.InstallmentTermService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class InstallmentTermServiceImpl implements InstallmentTermService {
    private InstallmentTermRepository installmentTermRepository;

    @Override
    public List<InstallmentTerm> createInstallmentTermsList(InstallmentExpense installmentExpense) {
        List<InstallmentTerm> installmentTermList = new ArrayList<>();

        for (int termNumber = 1; termNumber <= installmentExpense.getPaymentTerms(); termNumber++) {
            InstallmentTerm installmentTerm = new InstallmentTerm();
            installmentTerm.setTermNumber(termNumber);

            LocalDate dueDate;
            switch (installmentExpense.getPaymentFrequency()) {
                case MONTHLY -> dueDate = installmentExpense.getStartDate().plusMonths(termNumber);
                case WEEKLY  -> dueDate = installmentExpense.getStartDate().plusWeeks(termNumber);
                default -> throw new IllegalStateException(
                        "Unsupported payment frequency: " + installmentExpense.getPaymentFrequency()
                );
            }

            installmentTerm.setDueDate(dueDate);
            installmentTerm.setInstallmentExpense(installmentExpense);
            installmentTermList.add(installmentTerm);
        }

        return installmentTermList;
    }

    @Override
    public void deleteInstallmentTermsList(InstallmentExpense installmentExpense) {
        installmentTermRepository.deleteByInstallmentExpense(installmentExpense);
    }

    @Override
    public InstallmentTerm skipTermAndCreateNew(Long termId) {
        InstallmentTerm skippedTerm = installmentTermRepository.findById(termId)
                .orElseThrow(() -> new ResourceNotFoundException("Installment term not found"));

        if (skippedTerm.isSkipped()) {
            throw new IllegalStateException("Term is already skipped");
        }

        skippedTerm.setSkipped(true);
        installmentTermRepository.save(skippedTerm);

        InstallmentExpense expense = skippedTerm.getInstallmentExpense();

        InstallmentTerm lastTerm = installmentTermRepository
                .findTopByInstallmentExpenseOrderByTermNumberDesc(expense);

        InstallmentTerm newTerm = new InstallmentTerm();
        newTerm.setInstallmentExpense(expense);
        newTerm.setTermNumber(lastTerm.getTermNumber() + 1);

        LocalDate newDueDate;
        switch (expense.getPaymentFrequency()) {
            case MONTHLY -> newDueDate = lastTerm.getDueDate().plusMonths(1);
            case WEEKLY  -> newDueDate = lastTerm.getDueDate().plusWeeks(1);
            default -> throw new IllegalStateException("Unsupported frequency");
        }

        newTerm.setDueDate(newDueDate);

        return installmentTermRepository.save(newTerm);
    }
}
