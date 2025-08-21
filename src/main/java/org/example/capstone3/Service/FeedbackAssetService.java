package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Feedback;
import org.example.capstone3.Model.FeedbackAsset;
import org.example.capstone3.Repository.FeedbackAssetRepository;
import org.example.capstone3.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackAssetService {

    private final FeedbackAssetRepository feedbackAssetRepository;
    private final FeedbackRepository feedbackRepository;
    public List<FeedbackAsset> getAllFeedbackAsset(){
        return feedbackAssetRepository.findAll();
    }

    public void addFeedbackAsset(Integer feedback_id, MultipartFile file) throws IOException {
        Feedback feedback = feedbackRepository.findFeedbackById(feedback_id);
        if(feedback == null) {
            throw new ApiException("FeedBack Not found");
        }
        FeedbackAsset asset = new FeedbackAsset();
        asset.setFeedback(feedback);
        asset.setFileName(file.getOriginalFilename());
        asset.setFileContent(file.getBytes());
        feedbackAssetRepository.save(asset);
    }

    public FeedbackAsset getFeedBackAssetByID(Integer id){
        FeedbackAsset feedbackAsset = feedbackAssetRepository.findFeedbackAssetById(id);
        if(feedbackAsset == null)
            throw new ApiException("Feedback Asset Not Found");

        return feedbackAsset;
    }

    public void deleteFeedbackAsset(Integer id){
        FeedbackAsset feedbackAsset = feedbackAssetRepository.findFeedbackAssetById(id);
        if (feedbackAsset == null){
            throw new ApiException("Feedback Asset with id " + id + " not found");
        }
        feedbackAssetRepository.delete(feedbackAsset);
    }
}
