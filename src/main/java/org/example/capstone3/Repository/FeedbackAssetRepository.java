package org.example.capstone3.Repository;

import org.example.capstone3.Model.FeedbackAsset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackAssetRepository extends JpaRepository<FeedbackAsset, Integer> {
    FeedbackAsset findFeedbackAssetById(Integer id);

}
