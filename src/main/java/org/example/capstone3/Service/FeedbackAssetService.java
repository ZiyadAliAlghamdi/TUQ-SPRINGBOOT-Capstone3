package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.FeedbackAsset;
import org.example.capstone3.Repository.FeedbackAssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackAssetService {

    private final FeedbackAssetRepository feedbackAssetRepository;

    public List<FeedbackAsset> getAllFeedbackAsset(){
        return feedbackAssetRepository.findAll();
    }

    public void addFeedbackAsset(FeedbackAsset feedbackAsset){
        feedbackAssetRepository.save(feedbackAsset);
    }

    public void updateFeedbackAsset(Integer id , FeedbackAsset feedbackAsset){
        FeedbackAsset feedbackAsset1 = feedbackAssetRepository.findFeedbackAssetById(id);
        if (feedbackAsset1 == null){
            throw new ApiException("Feedback Asset with id " + id + " not found");
        }
        feedbackAsset1.setAssetType(feedbackAsset.getAssetType());
        feedbackAsset1.setMimeType(feedbackAsset.getMimeType());
        feedbackAsset1.setFileName(feedbackAsset.getFileName());
        feedbackAsset1.setFileContent(feedbackAsset.getFileContent());
        feedbackAsset1.setCreatedAt(feedbackAsset.getCreatedAt());
        feedbackAssetRepository.save(feedbackAsset1);
    }

    public void deleteFeedbackAsset(Integer id){
        FeedbackAsset feedbackAsset = feedbackAssetRepository.findFeedbackAssetById(id);
        if (feedbackAsset == null){
            throw new ApiException("Feedback Asset with id " + id + " not found");
        }
        feedbackAssetRepository.delete(feedbackAsset);
    }
}
