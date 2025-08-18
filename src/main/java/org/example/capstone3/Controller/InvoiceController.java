package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Invoice;
import org.example.capstone3.Service.InvoiceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllInvoice(){
        return ResponseEntity.status(200).body(invoiceService.getAllInvoice());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addInvoice(@RequestBody @Valid Invoice invoice){
        invoiceService.addInvoice(invoice);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateInvoice(@PathVariable Integer id, @RequestBody @Valid Invoice invoice){
        invoiceService.updateInvoice(id, invoice);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Integer id){
        invoiceService.deleteInvoice(id);
        return ResponseEntity.status(200).body(new ApiResponse("Invoice deleted successfully"));
    }
}
