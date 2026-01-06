package main.loantrackingbackend.controller;

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
@RequestMapping("/api/entry")
@AllArgsConstructor
public class PaymentAllocationController {

    private final PaymentAllocationService paymentAllocationService;

    @PostMapping("/{entryId}/payment-allocations")
    public ResponseEntity<PaymentAllocationResponseDto> createPaymentAllocation(@PathVariable UUID entryId, @RequestBody PaymentAllocationCreateDto dto) throws IOException {
        dto.setGroupExpenseEntryId(entryId);
        PaymentAllocationResponseDto response = paymentAllocationService.createPaymentAllocation(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/payment-allocations/{allocationId}")
    public ResponseEntity<PaymentAllocationResponseDto> getPaymentAllocationById(@PathVariable Long allocationId) {
        return ResponseEntity.ok(paymentAllocationService.getPaymentAllocationByAllocationId(allocationId));
    }

    @GetMapping("/{entryId}/payment-allocations")
    public ResponseEntity<List<PaymentAllocationResponseDto>> getAllocationsByEntry(@PathVariable UUID entryId) {
        return ResponseEntity.ok(paymentAllocationService.getPaymentAllocationById(entryId));
    }

    @GetMapping("/{entryId}/payment-allocations/member/{groupMemberPersonId}")
    public ResponseEntity<PaymentAllocationResponseDto> getAllocationsByMember(@PathVariable UUID entryId, @PathVariable Long groupMemberPersonId) {
        return ResponseEntity.ok(paymentAllocationService.getPaymentAllocationByGroupMember(entryId, groupMemberPersonId));
    }

    @GetMapping("/payment-allocations")
    public ResponseEntity<List<PaymentAllocationResponseDto>> getAllAllocations() {
        return ResponseEntity.ok(paymentAllocationService.getAllPaymentAllocations());
    }

    @DeleteMapping("/payment-allocations")
    public ResponseEntity<Void> deleteAllAllocations() {
        paymentAllocationService.deleteAllPaymentAllocations();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{entryId}/payment-allocations/member/{groupMemberPersonId}")
    public ResponseEntity<Void> deleteAllocationsByMember(@PathVariable UUID entryId, @PathVariable Long groupMemberPersonId) {
        paymentAllocationService.deletePaymentAllocationByGroupMember(entryId, groupMemberPersonId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{entryId}/payment-allocations")
    public ResponseEntity<Void> deleteAllocationsByEntry(@PathVariable UUID entryId) {
        paymentAllocationService.deletePaymentAllocationById(entryId);
        return ResponseEntity.noContent().build();
    }
}