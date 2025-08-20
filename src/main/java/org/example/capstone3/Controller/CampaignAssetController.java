package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.CampaignAsset;
import org.example.capstone3.Service.CampaignAssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/campaign-asset")
@RequiredArgsConstructor
public class CampaignAssetController {

    private final CampaignAssetService campaignAssetService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCampaignAsset(){
        return ResponseEntity.status(200).body(campaignAssetService.getAllCampaignAsset());
    }

    @PostMapping("/add/{campaign_id}")
    public ResponseEntity<?> addCampaignAsset(@PathVariable Integer campaign_id,@RequestBody @Valid CampaignAsset campaignAsset){
        campaignAssetService.addCampaignAsset(campaign_id,campaignAsset);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign Asset added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCampaignAsset(@PathVariable Integer id, @RequestBody @Valid CampaignAsset campaignAsset){
        campaignAssetService.updateCampaignAsset(id, campaignAsset);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign Asset updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCampaignAsset(@PathVariable Integer id){
        campaignAssetService.deleteCampaignAsset(id);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign Asset deleted successfully"));
    }
}
