package org.example.capstone3.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ResponseEntity<?> addAdvertiser(@RequestBody @Valid Advertiser advertiser) throws JsonProcessingException {
        advertiserService.addAdvertiser(advertiser);
        return ResponseEntity.status(200).body(new ApiResponse("Advertiser added successfully"));
    }

    @PostMapping("/request-otp/{id}/{actionType}")
    public ResponseEntity<?> requestOtpForAdvertiserAction(@PathVariable Integer id, @PathVariable String actionType) {
        advertiserService.requestOtpForAdvertiserAction(id, actionType);
        return ResponseEntity.status(200).body(new ApiResponse("OTP requested successfully. Check advertiser's email."));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAdvertiser(@PathVariable Integer id, @RequestBody @Valid Advertiser advertiser, @RequestParam String otp){
        advertiserService.updateAdvertiser(id, advertiser, otp);
        return ResponseEntity.status(200).body(new ApiResponse("Advertiser updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAdvertiser(@PathVariable Integer id, @RequestParam String otp){
        advertiserService.deleteAdvertiser(id, otp);
        return ResponseEntity.status(200).body(new ApiResponse("Advertiser deleted successfully"));
    }


    @GetMapping("{id}/campaigns")
    public ResponseEntity<?> getAdvertiserCampaigns(@PathVariable Integer id){
        return ResponseEntity.status(200).body(advertiserService.getAdvertiserCampaign(id));
    }

    @GetMapping("/competitor-analysis/{id}")
    public ResponseEntity<?> competitorAnalysis(@PathVariable Integer id){
        return ResponseEntity.status(200).body(advertiserService.competitorAnalysis(id));
    }
    @GetMapping("/suggest-campaign-ideas/{id}")
    public ResponseEntity<?> suggestCampaignIdeas(@PathVariable Integer id){
        return ResponseEntity.status(200).body(advertiserService.suggestCampaignIdeas(id));
    }
    @GetMapping("/summarize-feedback/{id}")
    public ResponseEntity<?> summarizeFeedback(@PathVariable Integer id){
        return ResponseEntity.status(200).body(advertiserService.summarizeFeedback(id));
    }
    @GetMapping("/monthly-strategy/{id}")
    public ResponseEntity<?> monthlyStrategy(@PathVariable Integer id){
        return ResponseEntity.status(200).body(advertiserService.monthlyStrategy(id));
    }
    @GetMapping("/compliance-check/{id}")
    public ResponseEntity<?> complianceCheck(@PathVariable Integer id){
        return ResponseEntity.status(200).body(advertiserService.complianceCheck(id));
    }

    @GetMapping("/advise-billboard-type/{advertiserId}")
    public ResponseEntity<?> adviseBillboardType(@PathVariable Integer advertiserId) {
        return ResponseEntity.ok(advertiserService.adviseBillboardType(advertiserId));
    }

    @GetMapping("/audience-demographics-analysis/{advertiserId}")
    public ResponseEntity<?> audienceDemographicsAnalysis(@PathVariable Integer advertiserId) {
        return ResponseEntity.ok(advertiserService.audienceDemographicsAnalysis(advertiserId));
    }

}
