package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.CampaignDTO;
import org.example.capstone3.Model.Advertiser;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Repository.AdvertiserRepository;
import org.example.capstone3.Repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final AdvertiserRepository advertiserRepository;
    public List<Campaign> getAllCampaign(){
        return campaignRepository.findAll();
    }

    public void addCampaign(CampaignDTO campaignDTO){
        Advertiser advertiser = advertiserRepository.findAdvertiserById(campaignDTO.getAdvertiser_id());
        if(advertiser == null)
            throw new ApiException("Advertiser Not Found");
        Campaign campaign = new Campaign(null,advertiser,null,null,null,campaignDTO.getName(),campaignDTO.getObjective(),campaignDTO.getLat(),campaignDTO.getLng());

        campaignRepository.save(campaign);
    }

    public void updateCampaign(Integer id , Campaign campaign){
        Campaign oldCampaign = campaignRepository.findCampaignById(id);
        if (oldCampaign == null){
            throw new ApiException("Campaign with id " + id + " not found");
        }
        oldCampaign.setName(campaign.getName());
        oldCampaign.setObjective(campaign.getObjective());
        oldCampaign.setLat(campaign.getLat());
        oldCampaign.setLng(campaign.getLng());
        campaignRepository.save(oldCampaign);
    }

    public void deleteCampaign(Integer id){
        Campaign campaign = campaignRepository.findCampaignById(id);
        if (campaign == null){
            throw new ApiException("Campaign with id " + id + " not found");
        }
        campaignRepository.delete(campaign);
    }
}
