package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.entity.Entry;
import main.loantrackingbackend.entity.InstallmentExpense;
import main.loantrackingbackend.entity.InstallmentTerm;
import main.loantrackingbackend.enums.InstallmentStatus;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.repository.EntryRepository;
import main.loantrackingbackend.repository.InstallmentTermRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/installment-terms")
@AllArgsConstructor
public class InstallmentTermController {

    private final InstallmentTermRepository termRepository;
    private final EntryRepository entryRepository;

    @GetMapping("/{termId}/status")
    public ResponseEntity<InstallmentStatus> getTermStatus(@PathVariable Long termId) {
        InstallmentTerm term = termRepository.findById(termId)
                .orElseThrow(() -> new ResourceNotFoundException("Installment term not found"));

        return ResponseEntity.ok(term.getInstallmentStatus());
    }

    @GetMapping("/view/{entryId}")
    public ResponseEntity<List<TermStatusDto>> getTermsPerInstallmentExpense(@PathVariable UUID entryId) {
        Entry entry = entryRepository.findEntryById(entryId);

        if (!(entry instanceof InstallmentExpense expense)) {
            throw new IllegalStateException("Entry is not an installment expense");
        }

        List<TermStatusDto> result = expense.getInstallmentTerms()
                .stream()
                .map(term -> {
                    return new TermStatusDto(
                            term.getTermId(),
                            term.getTermNumber(),
                            term.getDueDate(),
                            term.getInstallmentStatus()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(result);
    }

    @PostMapping("/{termId}/skip")
    public ResponseEntity<InstallmentStatus> skipTerm(@PathVariable Long termId) {
        InstallmentTerm term = termRepository.findById(termId)
                .orElseThrow(() -> new ResourceNotFoundException("Installment term not found"));

        term.setSkipped(true);
        return ResponseEntity.ok(term.getInstallmentStatus());
    }

    public record TermStatusDto(
            Long termId,
            int termNumber,
            java.time.LocalDate dueDate,
            InstallmentStatus status
    ) {}
}