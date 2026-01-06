package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.InstallmentTerm;
import main.loantrackingbackend.enums.InstallmentStatus;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.repository.EntryRepository;
import main.loantrackingbackend.repository.InstallmentTermRepository;
import main.loantrackingbackend.service.InstallmentTermService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/installment-terms")
@AllArgsConstructor
public class InstallmentTermController {

    private final InstallmentTermRepository termRepository;
    private final EntryRepository entryRepository;
    private final InstallmentTermService installmentTermService;

    @GetMapping("/{termId}")
    public ResponseEntity<TermResponseDto> getTerm(@PathVariable Long termId) {
        InstallmentTerm term = termRepository.findById(termId)
                .orElseThrow(() -> new ResourceNotFoundException("Installment term not found"));

        TermResponseDto dto = new TermResponseDto(
                term.getTermId(),
                term.getTermNumber(),
                term.getDueDate(),
                term.getInstallmentStatus()
        );
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/view/{entryId}")
    public ResponseEntity<List<TermResponseDto>> getTermsPerInstallmentExpense(@PathVariable UUID entryId) {
        Entry entry = entryRepository.findEntryById(entryId);

        if (entry == null) {
            throw new ResourceNotFoundException("Entry not found");
        }

        if (!(entry instanceof InstallmentExpense expense)) {
            throw new IllegalStateException("Entry is not an installment expense");
        }

        List<InstallmentTerm> terms = expense.getInstallmentTerms() != null
                ? expense.getInstallmentTerms()
                : Collections.emptyList();

        List<TermResponseDto> result = terms.stream()
                .sorted(Comparator.comparingInt(InstallmentTerm::getTermNumber))
                .map(term -> new TermResponseDto(
                        term.getTermId(),
                        term.getTermNumber(),
                        term.getDueDate(),
                        term.getInstallmentStatus()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/skip/{termId}")
    public ResponseEntity<TermResponseDto> skipTerm(@PathVariable Long termId) {
        InstallmentTerm newTerm = installmentTermService.skipTermAndCreateNew(termId);
        TermResponseDto dto = new TermResponseDto(
                newTerm.getTermId(),
                newTerm.getTermNumber(),
                newTerm.getDueDate(),
                newTerm.getInstallmentStatus()
        );
        return ResponseEntity.ok(dto);
    }

    public record TermResponseDto(
            Long termId,
            int termNumber,
            java.time.LocalDate dueDate,
            InstallmentStatus status
    ) {}
}