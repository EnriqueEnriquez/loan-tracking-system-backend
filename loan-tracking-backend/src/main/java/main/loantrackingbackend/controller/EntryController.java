package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.EntryResponseDto;
import main.loantrackingbackend.dto.InstallmentCreateDto;
import main.loantrackingbackend.dto.StraightCreateDto;
import main.loantrackingbackend.dto.StraightResponseDto;
import main.loantrackingbackend.entity.StraightExpense;
import main.loantrackingbackend.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/entry")
public class EntryController {
    private EntryService entryService;

    @GetMapping("{id}")
    public ResponseEntity<EntryResponseDto> getEntryById(@PathVariable("id") UUID entryID) throws IOException {
        EntryResponseDto entryResponseDto = entryService.getEntryById(entryID);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.OK);
    }

    @PostMapping(path = "/straight",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntryResponseDto> createStraightExpense(StraightCreateDto seCreateDto) throws IOException {
        EntryResponseDto entryResponseDto = entryService.createStraightExpense(seCreateDto);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.CREATED);
    }

    @PutMapping(path = "/straight/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<StraightResponseDto> updateStraightExpense(@PathVariable("id") UUID entryId, StraightCreateDto seUpdatedDto) throws IOException {
        StraightResponseDto straightResponseDto = entryService.updateStraightExpense(entryId, seUpdatedDto);

        return new ResponseEntity<>(straightResponseDto, HttpStatus.OK);
    }

    @PostMapping(path = "/installment",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntryResponseDto> createInstallmentExpense(InstallmentCreateDto installmentCreateDto) throws IOException {
        EntryResponseDto entryResponseDto = entryService.createInstallmentExpense(installmentCreateDto);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") UUID entryId) throws IOException {
        entryService.deleteEntry(entryId);
        return new ResponseEntity<>("Expense Entry Deleted", HttpStatus.OK);
    }

}
