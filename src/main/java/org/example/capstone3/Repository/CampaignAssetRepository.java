package org.example.capstone3.Repository;

import org.example.capstone3.Model.CampaignAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignAssetRepository extends JpaRepository<CampaignAsset, Integer> {
    CampaignAsset findCampaignAssetById(Integer id);
}
