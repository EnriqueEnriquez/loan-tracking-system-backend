package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.*;
import main.loantrackingbackend.entity.GroupExpense;
import main.loantrackingbackend.exception.ResourceNotFoundException;
import main.loantrackingbackend.service.EntryService;
import main.loantrackingbackend.service.PaymentAllocationService;
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
    private final EntryService entryService;
    private final PaymentAllocationService paymentAllocationService;
    
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

    @PutMapping(path = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<EntryResponseDto> updateEntryDetails(@PathVariable("id") UUID entryId, EntryUpdateDto updatedDto) throws IOException {
        EntryResponseDto entryResponseDto = entryService.updateEntryDetails(entryId, updatedDto);

        return new ResponseEntity<>(entryResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteExpense(@PathVariable("id") UUID entryId) throws IOException {
        entryService.deleteEntry(entryId);
        return new ResponseEntity<>("Expense Entry Deleted", HttpStatus.OK);
    }

    @PostMapping("/group-expense/{id}/divide-equally")
    public ResponseEntity<String> divideGroupExpenseEqually(@PathVariable("id") UUID groupExpenseId) {
        GroupExpense groupExpense = entryService.getGroupExpenseEntity(groupExpenseId);
        paymentAllocationService.divideEqually(groupExpense);
        return ResponseEntity.ok("Group Expense divided equally among members");
    }

    @PostMapping("/group-expense/{id}/divide-by-amount")
    public ResponseEntity<String> divideGroupExpenseByAmount(@PathVariable("id") UUID groupExpenseId,
                                                             @RequestBody List<PaymentAllocationCreateDto> allocations) {
        GroupExpense groupExpense = entryService.getGroupExpenseEntity(groupExpenseId);
        paymentAllocationService.divideByAmount(groupExpense, allocations);
        return ResponseEntity.ok("Group Expense divided by amount successfully");
    }

    @PostMapping("/group-expense/{id}/divide-by-percent")
    public ResponseEntity<String> divideGroupExpenseByPercent(@PathVariable("id") UUID groupExpenseId,
                                                              @RequestBody List<PaymentAllocationCreateDto> allocations) {
        GroupExpense groupExpense = entryService.getGroupExpenseEntity(groupExpenseId);
        paymentAllocationService.divideByPercent(groupExpense, allocations);
        return ResponseEntity.ok("Group Expense divided by percent successfully");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

}
