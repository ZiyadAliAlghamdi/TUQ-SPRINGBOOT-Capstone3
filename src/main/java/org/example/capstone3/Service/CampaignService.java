package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;

    public List<Campaign> getAllCampaign(){
        return campaignRepository.findAll();
    }

    public void addCampaign(Campaign campaign){
        campaignRepository.save(campaign);
    }

    public void updateCampaign(Integer id , Campaign campaign){
        Campaign campaign1 = campaignRepository.findCampaignById(id);
        if (campaign1 == null){
            throw new ApiException("Campaign with id " + id + " not found");
        }
        campaign1.setName(campaign.getName());
        campaign1.setObjective(campaign.getObjective());
        campaign1.setStatus(campaign.getStatus());
        campaignRepository.save(campaign1);
    }

    public void deleteCampaign(Integer id){
        Campaign campaign = campaignRepository.findCampaignById(id);
        if (campaign == null){
            throw new ApiException("Campaign with id " + id + " not found");
        }
        campaignRepository.delete(campaign);
    }
}
