package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.InstallmentResponseDto;
import main.loantrackingbackend.dto.InstallmentTermResponseDto;
import main.loantrackingbackend.entity.InstallmentTerm;

public class InstallmentTermMapper {
    public static InstallmentTermResponseDto mapToInstallmentResponseDto(InstallmentTerm installmentTerm) {
        InstallmentTermResponseDto installmentTermResponseDto = new InstallmentTermResponseDto();

        installmentTermResponseDto.setTermId(installmentTerm.getTermId());
        installmentTermResponseDto.setTermNumber(installmentTerm.getTermNumber());
        installmentTermResponseDto.setDueDate(installmentTerm.getDueDate());
        installmentTermResponseDto.setInstallmentExpenseEntryId(installmentTerm.getInstallmentExpense().getId());

        return installmentTermResponseDto;
    }
}
