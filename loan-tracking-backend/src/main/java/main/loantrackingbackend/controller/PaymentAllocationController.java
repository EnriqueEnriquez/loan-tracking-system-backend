/**package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PaymentAllocationCreateDto;
import main.loantrackingbackend.dto.PaymentAllocationResponseDto;
import main.loantrackingbackend.service.PaymentAllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/group-expense/payment-allocations")
@AllArgsConstructor
public class PaymentAllocationController {

    private final PaymentAllocationService paymentAllocationService;

    // Create a new payment allocation
    @PostMapping
    public ResponseEntity<PaymentAllocationResponseDto> createPaymentAllocation(
            @RequestBody PaymentAllocationCreateDto dto
    ) throws IOException {
        PaymentAllocationResponseDto response = paymentAllocationService.createPaymentAllocation(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Get payment allocation by ID
    @GetMapping("/{id}")
    public ResponseEntity<PaymentAllocationResponseDto> getPaymentAllocationById(
            @PathVariable("id") Long id
    ) {
        PaymentAllocationResponseDto response = paymentAllocationService.getPaymentAllocationById(id);
        return ResponseEntity.ok(response);
    }

    // Get all allocations for a specific group member
    @GetMapping("/member/{groupMemberPersonId}")
    public ResponseEntity<List<PaymentAllocationResponseDto>> getAllocationsByMember(
            @PathVariable("groupMemberPersonId") Long groupMemberPersonId
    ) {
        List<PaymentAllocationResponseDto> responses = paymentAllocationService.getPaymentAllocationsByPayee(groupMemberPersonId);
        return ResponseEntity.ok(responses);
    }

    // Get all allocations for a specific group expense
    @GetMapping("/entry/{entryId}")
    public ResponseEntity<List<PaymentAllocationResponseDto>> getAllocationsByEntry(
            @PathVariable("entryId") UUID entryId
    ) {
        List<PaymentAllocationResponseDto> responses = paymentAllocationService.getPaymentAllocationsByEntry(entryId);
        return ResponseEntity.ok(responses);
    }

    // Get all payment allocations
    @GetMapping
    public ResponseEntity<List<PaymentAllocationResponseDto>> getAllAllocations() {
        List<PaymentAllocationResponseDto> responses = paymentAllocationService.getAllPayments();
        return ResponseEntity.ok(responses);
    }

    // Delete all allocations
    @DeleteMapping
    public ResponseEntity<Void> deleteAllAllocations() {
        paymentAllocationService.deleteAllPaymentAllocations();
        return ResponseEntity.noContent().build();
    }

    // Delete allocations by group member
    @DeleteMapping("/member/{groupMemberPersonId}")
    public ResponseEntity<Void> deleteAllocationsByMember(
            @PathVariable("groupMemberPersonId") Long groupMemberPersonId
    ) {
        paymentAllocationService.deletePaymentAllocationsByGroupMember(groupMemberPersonId);
        return ResponseEntity.noContent().build();
    }

    // Delete allocations by group expense
    @DeleteMapping("/entry/{entryId}")
    public ResponseEntity<Void> deleteAllocationsByEntry(
            @PathVariable("entryId") UUID entryId
    ) {
        paymentAllocationService.deletePaymentAllocationsByEntry(entryId);
        return ResponseEntity.noContent().build();
    }
}*/