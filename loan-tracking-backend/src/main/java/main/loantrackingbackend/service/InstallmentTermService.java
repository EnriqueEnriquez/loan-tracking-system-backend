package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.InstallmentTermNotesDto;
import main.loantrackingbackend.dto.InstallmentTermResponseDto;
import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.InstallmentTerm;

import java.util.List;

public interface InstallmentTermService {
    List<InstallmentTerm> createInstallmentTermsList (InstallmentExpense installmentExpense);
    InstallmentTerm skipTermAndCreateNew(Long termId);
    InstallmentTermResponseDto updateInstallmentTermNotes(Long termId, InstallmentTermNotesDto newNotes);
    void deleteInstallmentTermsList(InstallmentExpense installmentExpense);
}
