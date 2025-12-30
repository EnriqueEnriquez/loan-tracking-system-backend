package main.loantrackingbackend.service;

import main.loantrackingbackend.dto.StraightCreateDto;
import main.loantrackingbackend.dto.StraightResponseDto;

import java.io.IOException;

public interface EntryService {
    StraightResponseDto createStraightExpense(StraightCreateDto seCreateDto) throws IOException;
}
