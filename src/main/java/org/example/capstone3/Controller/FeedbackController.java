package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTO.FeedbackDTO;
import org.example.capstone3.Model.Feedback;
import org.example.capstone3.Model.Mail;
import org.example.capstone3.Service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackService feedbackService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllFeedback(){
        return ResponseEntity.status(200).body(feedbackService.getAllFeedback());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createFeedback(@RequestBody @Valid FeedbackDTO feedbackDTO){
        feedbackService.addFeedback(feedbackDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateFeedback(@PathVariable Integer id, @RequestBody @Valid FeedbackDTO feedbackDTO){
        feedbackService.updateFeedback(id, feedbackDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer id){
        feedbackService.deleteFeedback(id);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback deleted successfully"));
    }

    @GetMapping("/get/opened")
    public ResponseEntity<?> getOpenedFeedbacks(){
        return ResponseEntity.ok(feedbackService.getOpenedFeedbacks());
    }

    @PutMapping("/{id}/close")
    public ResponseEntity<?> closingFeedback(@PathVariable Integer id, @RequestBody Mail mail){
        feedbackService.closingFeedback(id, mail);
        return ResponseEntity.status(200).body(new ApiResponse("feedback is closed"));
    }

    @GetMapping("/get/closed")
    public ResponseEntity<?> getClosedFeedbacks(){
        return ResponseEntity.ok(feedbackService.getClosedFeedback());
    }


}
