package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.StraightExpense;

public class EntryMapper {

    private static void mapToCommonDtoFields(Entry source, EntryResponseDto target) {
        target.setId(source.getId());
        target.setEntryName(source.getEntryName());
        target.setDescription(source.getDescription());
        target.setTransactionType(source.getTransactionType());
        target.setDateBorrowed(source.getDateBorrowed());
        target.setDateFullyPaid(source.getDateFullyPaid());
        target.setBorrowerName(source.getBorrowerName());
        target.setLenderName(source.getLenderName());
        target.setAmountBorrowed(source.getAmountBorrowed());
        target.setAmountRemaining(source.getAmountRemaining());
        target.setStatus(source.getStatus());
        target.setNotes(source.getNotes());

        if (source.getImageProof() != null) {
            target.setImageUrl(source.getImageProof().getImageUrl());
        } else {
            target.setImageUrl(null);
        }
    }

    public static StraightResponseDto mapToStraightResponseDto(StraightExpense straightExpense) {
        StraightResponseDto dto = new StraightResponseDto();

        mapToCommonDtoFields(straightExpense, dto);
        dto.setBorrowerId(straightExpense.getPersonBorrower().getPersonId());

        return dto;
    }

    public static InstallmentResponseDto mapToInstallmentResponseDto (InstallmentExpense installmentExpense) {
        InstallmentResponseDto dto = new InstallmentResponseDto();

        mapToCommonDtoFields(installmentExpense, dto);

        dto.setBorrowerId(installmentExpense.getPersonBorrower().getPersonId());
        dto.setStartDate(installmentExpense.getStartDate());
        dto.setPaymentFrequency(installmentExpense.getPaymentFrequency());
        dto.setPaymentTerms(installmentExpense.getPaymentTerms());
        dto.setPaymentAmountPerTerm(installmentExpense.getPaymentAmountPerTerm());

        return dto;
    }

    private static void mapCommonEntityFields(EntryCreateDto source, Entry target) {
        target.setId(source.getId());
        target.setEntryName(source.getEntryName());
        target.setDescription(source.getDescription());
        target.setTransactionType(source.getTransactionType());
        target.setDateBorrowed(source.getDateBorrowed());
        target.setDateFullyPaid(source.getDateFullyPaid());
        target.setBorrowerName(source.getBorrowerName());
        target.setLenderName(source.getLenderName());
        target.setAmountBorrowed(source.getAmountBorrowed());
        target.setAmountRemaining(source.getAmountRemaining());
        target.setStatus(source.getStatus());
        target.setNotes(source.getNotes());
    }

    public static StraightExpense mapToStraightExpense(StraightCreateDto dto) {
        StraightExpense straightExpense = new StraightExpense();
        mapCommonEntityFields(dto, straightExpense);

        return straightExpense;
    }

    public static InstallmentExpense mapToInstallmentExpense(InstallmentCreateDto dto) {
        InstallmentExpense installmentExpense = new InstallmentExpense();
        mapCommonEntityFields(dto, installmentExpense);

        installmentExpense.setStartDate(dto.getStartDate());
        installmentExpense.setPaymentFrequency(dto.getPaymentFrequency());
        installmentExpense.setPaymentTerms(dto.getPaymentTerms());
        installmentExpense.setPaymentAmountPerTerm(dto.getPaymentAmountPerTerm());

        return installmentExpense;
    }

}
