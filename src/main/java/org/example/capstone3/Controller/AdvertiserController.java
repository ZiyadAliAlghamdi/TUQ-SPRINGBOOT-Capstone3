package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Advertiser;
import org.example.capstone3.Service.AdvertiserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/advertiser")
@RequiredArgsConstructor
public class AdvertiserController {

    private final AdvertiserService advertiserService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllAdvertiser(){
        return ResponseEntity.status(200).body(advertiserService.getAllAdvertiser());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addAdvertiser(@RequestBody @Valid Advertiser advertiser){
        advertiserService.addAdvertiser(advertiser);
        return ResponseEntity.status(200).body(new ApiResponse("Advertiser added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdvertiser(@PathVariable Integer id, @RequestBody @Valid Advertiser advertiser){
        advertiserService.updateAdvertiser(id, advertiser);
        return ResponseEntity.status(200).body(new ApiResponse("Advertiser updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdvertiser(@PathVariable Integer id){
        advertiserService.deleteAdvertiser(id);
        return ResponseEntity.status(200).body(new ApiResponse("Advertiser deleted successfully"));
    }
}
