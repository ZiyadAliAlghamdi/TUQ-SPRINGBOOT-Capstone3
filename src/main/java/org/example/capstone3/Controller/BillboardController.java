package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTO.BillboardDTO;
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
    public ResponseEntity<?> addBillboard(@RequestBody @Valid BillboardDTO billboard){
        billboardService.addBillboard(billboard);
        return ResponseEntity.status(200).body(new ApiResponse("Billboard added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBillboard(@PathVariable Integer id, @RequestBody @Valid BillboardDTO billboard){
        billboardService.updateBillboard(id, billboard);
        return ResponseEntity.status(200).body(new ApiResponse("Billboard updated successfully"));
    }

    @DeleteMapping("/delete/{lessor_id}/{billboard_id}")
    public ResponseEntity<?> deleteBillboard(@PathVariable Integer lessor_id,@PathVariable Integer billboard_id){
        billboardService.deleteBillboard(lessor_id, billboard_id);
        return ResponseEntity.status(200).body(new ApiResponse("Billboard deleted successfully"));
    }

    @GetMapping("/district/{district}")
    public ResponseEntity<?> findBillboardsByDistrict(@PathVariable String district) {
        return ResponseEntity.status(200).body(billboardService.findBillboardsByDistrict(district));
    }

    @GetMapping("/size/{height}/{width}")
    public ResponseEntity<?> findBillboardsByHeightAndWidth(@PathVariable Double height,
                                                            @PathVariable Double width) {
        return ResponseEntity.status(200).body(billboardService.findBillboardsByHeightAndWidth(height, width));
    }

    @GetMapping("/lessor-rating/")
    public ResponseEntity<?> findBillboardsByLessorRating() {
        return ResponseEntity.status(200).body(billboardService.findBillboardsByLessor_RatingAvg());
    }

    @GetMapping("/availability/{status}")
    public ResponseEntity<?> findBillboardsByAvailabilityStatus(@PathVariable("status") String availabilityStatus) {
        return ResponseEntity.status(200).body(billboardService.findBillboardsByAvailabilityStatus(availabilityStatus));
    }
}
