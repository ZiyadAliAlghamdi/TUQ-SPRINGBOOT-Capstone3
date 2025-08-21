package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Lessor;
import org.example.capstone3.Service.LessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/lessor")
@RequiredArgsConstructor
public class LessorController {

    private final LessorService lessorService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllLessor(){
        return ResponseEntity.status(200).body(lessorService.getAllLessor());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addLessor(@RequestBody @Valid Lessor lessor){
        lessorService.addLessor(lessor);
        return ResponseEntity.status(200).body(new ApiResponse("Lessor added successfully"));
    }

    @PostMapping("/request-otp/{id}/{actionType}")
    public ResponseEntity<?> requestLessorOtp(@PathVariable Integer id, @PathVariable String actionType) {
        lessorService.requestOtpForLessorAction(id, actionType);
        return ResponseEntity.status(200).body(new ApiResponse("OTP requested successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLessor(@PathVariable Integer id, @RequestBody @Valid Lessor lessor, @RequestParam String otp){
        lessorService.updateLessor(id, lessor, otp);
        return ResponseEntity.status(200).body(new ApiResponse("Lessor updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteLessor(@PathVariable Integer id, @RequestParam String otp){
        lessorService.deleteLessor(id, otp);
        return ResponseEntity.status(200).body(new ApiResponse("Lessor deleted successfully"));
    }

    @GetMapping("{id}/billboards")
    public ResponseEntity<?> getBillboardByLessor(@PathVariable Integer id){
        return ResponseEntity.ok(lessorService.getLessorBillboards(id));
    }

    @GetMapping("{id}/invoices")
    public ResponseEntity<?> getLessorInvoices(@PathVariable Integer id){
        return ResponseEntity.ok(lessorService.getLessorInvoices(id));
    }



}
