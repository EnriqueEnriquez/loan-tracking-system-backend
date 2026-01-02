package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.entity.InstallmentExpense;

import java.io.IOException;
import java.util.UUID;

public interface EntryService {

    // Get all entries (for dashboard)
    //TODO: List<EntryResponseDto> getAllEntries()

    // Get specific details regardless of type
    EntryResponseDto getEntryById(UUID entryId);

    //TODO: Try to create a generic method for updating an entry
    public EntryResponseDto updateEntryDetails(UUID entryId, EntryUpdateDto updateDto) throws IOException;

    // Delete any type of entry
    void deleteEntry(UUID entryId) throws IOException;

    StraightResponseDto createStraightExpense(StraightCreateDto seCreateDto) throws IOException;

    StraightResponseDto updateStraightExpense(UUID entryID, StraightCreateDto seCreateDto) throws IOException;

    InstallmentResponseDto createInstallmentExpense(InstallmentCreateDto installmentCreateDto) throws IOException;
}
