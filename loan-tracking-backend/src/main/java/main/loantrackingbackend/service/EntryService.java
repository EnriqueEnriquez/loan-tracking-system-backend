package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.entity.InstallmentExpense;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface EntryService {

    // Get all entries (for dashboard)
    Map<String, List<EntryResponseDto>> getAllEntriesGrouped();

    // Get specific details regardless of type
    EntryResponseDto getEntryById(UUID entryId);

    //TODO: Try to create a generic method for updating an entry
    public EntryResponseDto updateEntryDetails(UUID entryId, EntryUpdateDto updateDto) throws IOException;

    // Delete any type of entry
    void deleteEntry(UUID entryId) throws IOException;

    StraightResponseDto createStraightExpense(StraightCreateDto seCreateDto) throws IOException;

    InstallmentResponseDto createInstallmentExpense(InstallmentCreateDto installmentCreateDto) throws IOException;

    GroupExpenseResponseDto createGroupExpense(GroupExpenseCreateDto geCreateDto) throws IOException;

    GroupExpense getGroupExpenseEntity(UUID entryId);
}
