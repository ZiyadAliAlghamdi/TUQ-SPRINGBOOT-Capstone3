package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.DTO.InvoiceDTO;
import org.example.capstone3.Service.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/pay")
    public ResponseEntity<?> processPayment(@RequestBody InvoiceDTO invoiceDTO){
        return ResponseEntity.status(200).body(invoiceService.processPayment(invoiceDTO));
    }

    @GetMapping("/get_status/{id}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getPaymentStatus(id));
    }

    @GetMapping("/callback")
    public ResponseEntity<String> handlePaymentCallback(@RequestParam String id,
                                                        @RequestParam String status,
                                                        @RequestParam String amount,
                                                        @RequestParam String message) {
        invoiceService.handlePaymentCallback(id, status, amount, message);
        return ResponseEntity.ok("Callback received");
    }
}
