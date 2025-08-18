package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.FeedbackAsset;
import org.example.capstone3.Service.FeedbackAssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback-asset")
@RequiredArgsConstructor
public class FeedbackAssetController {

    private final FeedbackAssetService feedbackAssetService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllFeedbackAsset(){
        return ResponseEntity.status(200).body(feedbackAssetService.getAllFeedbackAsset());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addFeedbackAsset(@RequestBody @Valid FeedbackAsset feedbackAsset){
        feedbackAssetService.addFeedbackAsset(feedbackAsset);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback Asset added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFeedbackAsset(@PathVariable Integer id, @RequestBody @Valid FeedbackAsset feedbackAsset){
        feedbackAssetService.updateFeedbackAsset(id, feedbackAsset);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback Asset updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFeedbackAsset(@PathVariable Integer id){
        feedbackAssetService.deleteFeedbackAsset(id);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback Asset deleted successfully"));
    }
}
