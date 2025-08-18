package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.CampaignAsset;
import org.example.capstone3.Repository.CampaignAssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignAssetService {

    private final CampaignAssetRepository campaignAssetRepository;

    public List<CampaignAsset> getAllCampaignAsset(){
        return campaignAssetRepository.findAll();
    }

    public void addCampaignAsset(CampaignAsset campaignAsset){
        campaignAssetRepository.save(campaignAsset);
    }

    public void updateCampaignAsset(Integer id , CampaignAsset campaignAsset){
        CampaignAsset campaignAsset1 = campaignAssetRepository.findCampaign_AssetById(id);
        if (campaignAsset1 == null){
            throw new ApiException("Campaign Asset with id " + id + " not found");
        }
        campaignAsset1.setAssetType(campaignAsset.getAssetType());
        campaignAsset1.setMimeType(campaignAsset.getMimeType());
        campaignAsset1.setFileName(campaignAsset.getFileName());
        campaignAsset1.setBlob(campaignAsset.getBlob());
        campaignAsset1.setCreatedAt(campaignAsset.getCreatedAt());
        campaignAssetRepository.save(campaignAsset1);
    }

    public void deleteCampaignAsset(Integer id){
        CampaignAsset campaignAsset = campaignAssetRepository.findCampaign_AssetById(id);
        if (campaignAsset == null){
            throw new ApiException("Campaign Asset with id " + id + " not found");
        }
        campaignAssetRepository.delete(campaignAsset);
    }
}
