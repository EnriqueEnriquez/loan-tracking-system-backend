package main.loantrackingbackend.mapper;

import main.loantrackingbackend.dto.StraightCreateDto;
import main.loantrackingbackend.dto.StraightResponseDto;
import main.loantrackingbackend.entity.StraightExpense;

public class EntryMapper {

    public static StraightResponseDto mapToStraightExpenseDto(StraightExpense straightExpense) {
        StraightResponseDto dto = new StraightResponseDto();

        dto.setId(straightExpense.getId());
        dto.setEntryName(straightExpense.getEntryName());
        dto.setDescription(straightExpense.getDescription());
        dto.setTransactionType(straightExpense.getTransactionType());
        dto.setDateBorrowed(straightExpense.getDateBorrowed());
        dto.setDateFullyPaid(straightExpense.getDateFullyPaid());
        dto.setBorrowerName(straightExpense.getBorrowerName());
        dto.setLenderName(straightExpense.getLenderName());
        dto.setAmountBorrowed(straightExpense.getAmountBorrowed());
        dto.setAmountRemaining(straightExpense.getAmountRemaining());
        dto.setStatus(straightExpense.getStatus());
        dto.setNotes(straightExpense.getNotes());
        dto.setBorrowerId(straightExpense.getPersonBorrower().getPersonId());
        dto.setImageUrl(straightExpense.getImageProof().getImageUrl());

        return dto;
    }

    public static StraightExpense mapToStraightExpense(StraightCreateDto dto) {
        StraightExpense se = new StraightExpense();

        se.setId(dto.getId());
        se.setEntryName(dto.getEntryName());
        se.setDescription(dto.getDescription());
        se.setTransactionType(dto.getTransactionType());
        se.setDateBorrowed(dto.getDateBorrowed());
        se.setDateFullyPaid(dto.getDateFullyPaid());
        se.setBorrowerName(dto.getBorrowerName());
        se.setLenderName(dto.getLenderName());
        se.setAmountBorrowed(dto.getAmountBorrowed());
        se.setAmountRemaining(dto.getAmountRemaining());
        se.setStatus(dto.getStatus());
        se.setNotes(dto.getNotes());

        return se;
    }




}
