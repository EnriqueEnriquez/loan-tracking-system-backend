package main.loantrackingbackend.service.impl;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.InstallmentTerm;
import main.loantrackingbackend.enums.PaymentFrequency;
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
            if (installmentExpense.getPaymentFrequency() == PaymentFrequency.MONTHLY) {
                dueDate = installmentExpense.getStartDate().plusMonths(termNumber);
            } else {
                dueDate = installmentExpense.getStartDate().plusWeeks(termNumber);
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
}
