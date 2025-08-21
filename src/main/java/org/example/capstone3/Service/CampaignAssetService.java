package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Model.CampaignAsset;
import org.example.capstone3.Repository.CampaignAssetRepository;
import org.example.capstone3.Repository.CampaignRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignAssetService {

    private final CampaignAssetRepository campaignAssetRepository;
    private final CampaignRepository campaignRepository;
    public List<CampaignAsset> getAllCampaignAsset(){
        return campaignAssetRepository.findAll();
    }

    public void addCampaignAsset(Integer campaign_id, MultipartFile file) throws IOException {
        Campaign campaign = campaignRepository.findCampaignById(campaign_id);
        if(campaign == null) {
            throw new ApiException("Campaign Not found");
        }
        CampaignAsset asset = new CampaignAsset();
        asset.setCampaign(campaign);
        asset.setFileName(file.getOriginalFilename());
        asset.setFileContent(file.getBytes());
        campaignAssetRepository.save(asset);
    }

    public CampaignAsset getCampaignAssetById(Integer id){
        CampaignAsset campaignAsset = campaignAssetRepository.findCampaignAssetById(id);
        if(campaignAsset == null)
            throw new ApiException("Campaign Asset Not Found");

        return campaignAsset;
    }

    public void updateCampaignAsset(Integer id , CampaignAsset campaignAsset){
        CampaignAsset oldCampaignAsset = campaignAssetRepository.findCampaignAssetById(id);
        if (oldCampaignAsset == null) {
            throw new ApiException("Campaign Asset with id " + id + " not found");
        }
        oldCampaignAsset.setFileName(campaignAsset.getFileName());
        oldCampaignAsset.setFileContent(campaignAsset.getFileContent());
        campaignAssetRepository.save(oldCampaignAsset);
    }

    public void deleteCampaignAsset(Integer id){
        CampaignAsset campaignAsset = campaignAssetRepository.findCampaignAssetById(id);
        if (campaignAsset == null){
            throw new ApiException("Campaign Asset with id " + id + " not found");
        }
        campaignAssetRepository.delete(campaignAsset);
    }
}
