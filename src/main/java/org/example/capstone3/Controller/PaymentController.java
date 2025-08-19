package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Model.Payment;
import org.example.capstone3.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/card")
    public ResponseEntity<?> processPayment(@RequestBody Payment payment){
        return ResponseEntity.status(200).body(paymentService.processPayment(payment));
    }

    @GetMapping("/get_status/{id}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(paymentService.getPaymentStatus(id));
    }
}
