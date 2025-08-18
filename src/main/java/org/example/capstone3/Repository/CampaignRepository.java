package org.example.capstone3.Repository;

import org.example.capstone3.Model.Campaign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CampaignRepository extends JpaRepository<Campaign, Integer> {
    Campaign findCampaignById(Integer id);
}
