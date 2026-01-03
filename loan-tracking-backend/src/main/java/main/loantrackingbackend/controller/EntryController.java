package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.service.EntryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/entry")
public class EntryController {
    private EntryService entryService;

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<EntryResponseDto>>> getAllEntriesGrouped() throws IOException {
        Map<String, List<EntryResponseDto>> allEntriesGrouped = entryService.getAllEntriesGrouped();

        return new ResponseEntity<>(allEntriesGrouped, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntryResponseDto> getEntryById(@PathVariable("id") UUID entryID) throws IOException {
        EntryResponseDto entryResponseDto = entryService.getEntryById(entryID);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.OK);
    }

    @PostMapping(path = "/straight",consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EntryResponseDto> createStraightExpense(StraightCreateDto seCreateDto) throws IOException {
        EntryResponseDto entryResponseDto = entryService.createStraightExpense(seCreateDto);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.CREATED);
    }

    @PutMapping( path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<EntryResponseDto> updateEntryDetails(@PathVariable("id") UUID entryId, EntryUpdateDto updatedDto) throws IOException {
        EntryResponseDto entryResponseDto = entryService.updateEntryDetails(entryId, updatedDto);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.OK);
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
