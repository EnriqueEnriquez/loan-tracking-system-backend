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

    @GetMapping("/by/{payeeId}")
    public ResponseEntity<List<PaymentResponseDto>> getPaymentsByPayee(@PathVariable Long payeeId) {
        List<PaymentResponseDto> payments = paymentService.getPaymentsByPayee(payeeId);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAllPayments());
    }

}