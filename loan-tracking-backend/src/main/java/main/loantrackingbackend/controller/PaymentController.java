package main.loantrackingbackend.controller;

import lombok.AllArgsConstructor;
import main.loantrackingbackend.dto.PaymentCreateDto;
import main.loantrackingbackend.dto.PaymentResponseDto;
import main.loantrackingbackend.service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
@AllArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PaymentResponseDto> createPayment(@ModelAttribute PaymentCreateDto dto) throws IOException {
        PaymentResponseDto response = paymentService.createPayment(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{paymentId}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long paymentId) {
        PaymentResponseDto response = paymentService.getPaymentById(paymentId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/by/{personId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByPayee(@PathVariable Long personId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsByPayee(personId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/entry/{entryId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByEntry(@PathVariable UUID entryId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsByEntry(entryId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/allocation/{entryId}/{personId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsForAllocation(@PathVariable UUID entryId, @PathVariable Long memberPersonId){
        List<PaymentResponseDto> payments = paymentService.getPaymentsForAllocation(entryId, memberPersonId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

    @DeleteMapping("/all")
    public ResponseEntity<String> deleteAllPayments() {
        paymentService.deleteAllPayments();
        return ResponseEntity.ok("Successfully deleted all payments.");
    }

    @DeleteMapping("/by/{personId}")
    public ResponseEntity<String> deletePaymentsByPayee(@PathVariable Long personId) {
        paymentService.deletePaymentsByPayee(personId);
        return ResponseEntity.ok("Successfully deleted payments for payee " + personId);
    }

    @DeleteMapping("/entry/{entryId}")
    public ResponseEntity<String> deletePaymentsByEntry(@PathVariable UUID entryId) {
        paymentService.deletePaymentsByEntry(entryId);
        return ResponseEntity.ok("Successfully deleted payments for entry " + entryId);
    }
}
