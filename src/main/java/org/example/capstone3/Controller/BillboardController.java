package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Billboard;
import org.example.capstone3.Service.BillboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/billboard")
@RequiredArgsConstructor
public class BillboardController {

    private final BillboardService billboardService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllBillboard(){
        return ResponseEntity.status(200).body(billboardService.getAllBillboard());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBillboard(@RequestBody @Valid Billboard billboard){
        billboardService.addBillboard(billboard);
        return ResponseEntity.status(200).body(new ApiResponse("Billboard added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBillboard(@PathVariable Integer id, @RequestBody @Valid Billboard billboard){
        billboardService.updateBillboard(id, billboard);
        return ResponseEntity.status(200).body(new ApiResponse("Billboard updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBillboard(@PathVariable Integer id){
        billboardService.deleteBillboard(id);
        return ResponseEntity.status(200).body(new ApiResponse("Billboard deleted successfully"));
    }
}
