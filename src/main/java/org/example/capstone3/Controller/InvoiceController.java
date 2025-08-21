package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
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


    @GetMapping("/get")
    public ResponseEntity<?> getAllInvoices(){
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/get_status/{id}")
    public ResponseEntity<?> getPaymentStatus(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.OK).body(invoiceService.getPaymentStatus(id));
    }

    @PostMapping("/pay/{bookingId}")
    public ResponseEntity<?> processPaymentBooking(@PathVariable Integer bookingId,@RequestBody InvoiceDTO invoiceDTO){
        return ResponseEntity.status(200).body(invoiceService.processPayment(bookingId,invoiceDTO));
    }

    @PostMapping("/pay/subscription/{advertiserId}")
    public ResponseEntity<?> processPaymentSubscription(@PathVariable Integer advertiserId,@Valid @RequestBody InvoiceDTO invoiceDTO){
        return ResponseEntity.status(200).body(invoiceService.processPaymentForSubscription(advertiserId,invoiceDTO));
    }


    @PutMapping("/callback")
    public ResponseEntity<?> handlePaymentCallback(@RequestParam String id,
                                                        @RequestParam String status,
                                                        @RequestParam String amount,
                                                        @RequestParam String message) {
        invoiceService.handlePaymentCallback(id, status, amount, message);
        return ResponseEntity.status(200).body(new ApiResponse("Payment status("+status+")"));
    }
}
