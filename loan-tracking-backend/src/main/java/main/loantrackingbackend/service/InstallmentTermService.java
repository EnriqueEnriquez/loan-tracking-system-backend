package main.loantrackingbackend.service;

import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.InstallmentTerm;

import java.util.List;

public interface InstallmentTermService {
    List<InstallmentTerm> createInstallmentTermsList (InstallmentExpense installmentExpense);
    InstallmentTerm skipTermAndCreateNew(Long termId);
    void deleteInstallmentTermsList(InstallmentExpense installmentExpense);
}
