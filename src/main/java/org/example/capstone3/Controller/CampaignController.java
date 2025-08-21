package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTO.CampaignDTO;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Service.CampaignService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/campaign")
@RequiredArgsConstructor
public class CampaignController {

    private final CampaignService campaignService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCampaign(){
        return ResponseEntity.status(200).body(campaignService.getAllCampaign());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCampaign(@RequestBody @Valid CampaignDTO campaign){
        campaignService.addCampaign(campaign);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCampaign(@PathVariable Integer id, @RequestBody @Valid Campaign campaign){
        campaignService.updateCampaign(id, campaign);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCampaign(@PathVariable Integer id){
        campaignService.deleteCampaign(id);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign deleted successfully"));
    }

    @GetMapping("/advise/{id}")
    public ResponseEntity<?> adviseCampaign(@PathVariable Integer id){
        return ResponseEntity.status(200).body(campaignService.adviseCampaign(id));
    }

    @GetMapping("/predict-performance/{id}")
    public ResponseEntity<?> predictPerformance(@PathVariable Integer id){
        return ResponseEntity.status(200).body(campaignService.predictPerformance(id));
    }









}

