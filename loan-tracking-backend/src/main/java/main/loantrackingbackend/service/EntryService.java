package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.StraightCreateDto;
import main.loantrackingbackend.dto.StraightResponseDto;

import java.io.IOException;
import java.util.UUID;

public interface EntryService {

    StraightResponseDto createStraightExpense(StraightCreateDto seCreateDto) throws IOException;

    // Get all entries (for dashboard)
    //TODO: List<EntryResponseDto> getAllEntries()

    // Get specific details regardless of type
    //TODO: EntryResponseDto getEntryById(UUID entryId);

    // Delete any type of entry
    //TODO: void deleteEntry(UUID entryId);

    StraightResponseDto getStraightExpenseByID(UUID entryID) throws IOException;

    StraightResponseDto updateStraightExpense(UUID entryID, StraightCreateDto seCreateDto) throws IOException;

    void deleteStraightExpense(UUID entryID) throws IOException;
}
