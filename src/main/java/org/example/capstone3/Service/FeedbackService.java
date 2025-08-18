package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Feedback;
import org.example.capstone3.Repository.FeedbackRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;

    public List<Feedback> getAllFeedback(){
        return feedbackRepository.findAll();
    }

    public void addFeedback(Feedback feedback){
        feedbackRepository.save(feedback);
    }

    public void updateFeedback(Integer id , Feedback feedback){
        Feedback feedback1 = feedbackRepository.findFeedbackById(id);
        if (feedback1 == null){
            throw new ApiException("Feedback with id " + id + " not found");
        }
        feedback1.setType(feedback.getType());
        feedback1.setScore(feedback.getScore());
        feedback1.setComment(feedback.getComment());
        feedback1.setType(feedback.getType());
        feedback1.setScore(feedback.getScore());
        feedback1.setComment(feedback.getComment());
        feedback1.setCreatedAt(feedback.getCreatedAt());
        feedbackRepository.save(feedback1);
    }

    public void deleteFeedback(Integer id){
        Feedback feedback = feedbackRepository.findFeedbackById(id);
        if (feedback == null){
            throw new ApiException("Feedback with id " + id + " not found");
        }
        feedbackRepository.delete(feedback);
    }
}
