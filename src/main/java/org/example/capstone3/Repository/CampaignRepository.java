package org.example.capstone3.Repository;

import org.example.capstone3.Model.Advertiser;
import org.example.capstone3.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    Campaign findCampaignById(Integer id);

    List<Campaign> findCampaignByAdvertiser(Advertiser advertiser);
}
