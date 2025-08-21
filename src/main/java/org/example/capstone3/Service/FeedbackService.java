package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.FeedbackDTO;
import org.example.capstone3.Model.*;
import org.example.capstone3.Repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final AdvertiserRepository advertiserRepository;
    private final LessorRepository lessorRepository;
    private final CampaignRepository campaignRepository;
    private final BookingRepository bookingRepository;

    private final MailService mailService;


    public List<Feedback> getAllFeedback(){
        return feedbackRepository.findAll();
    }

    public void addFeedback(FeedbackDTO feedbackDTO) {
        // Load everything (use Optional in your repos ideally)
        Advertiser advertiser = advertiserRepository.findAdvertiserById(feedbackDTO.getAdvertiser_id());
        Lessor      lessor    = lessorRepository.findLessorById(feedbackDTO.getLessor_id());
        Campaign    campaign  = campaignRepository.findCampaignById(feedbackDTO.getCampaign_id());
        Booking     booking   = bookingRepository.findBookingById(feedbackDTO.getBooking_id());

        if (advertiser == null || lessor == null || campaign == null || booking == null) {
            throw new ApiException("advertiser/lessor/campaign/booking Not Found");
        }

        // ---------- RELATIONSHIP CONSISTENCY CHECKS ----------

        if (booking.getBillboard() == null ||
                booking.getBillboard().getLessor() == null ||
                !booking.getBillboard().getLessor().getId().equals(lessor.getId())) {
            throw new ApiException("Booking does not belong to this lessor");
        }

        // Booking must be tied to this campaign
        if (booking.getCampaign() == null ||
                !booking.getCampaign().getId().equals(campaign.getId())) {
            throw new ApiException("Booking does not belong to this campaign");
        }

        // Campaign must belong to this advertiser
        if (campaign.getAdvertiser() == null ||
                !campaign.getAdvertiser().getId().equals(advertiser.getId())) {
            throw new ApiException("Campaign does not belong to this advertiser");
        }



        // Prevent duplicate feedback for the same booking/advertiser
        if (feedbackRepository.existsByBooking_IdAndAdvertiser_Id(feedbackDTO.getBooking_id(), feedbackDTO.getAdvertiser_id())) {
            throw new ApiException("Feedback already submitted for this booking by this advertiser");
        }

        if (feedbackDTO.getScore() == null || feedbackDTO.getScore() < 1 || feedbackDTO.getScore() > 5) {
            throw new ApiException("Score must be between 1 and 5");
        }

        // ---------- CREATE FEEDBACK ----------
        Feedback feedback = new Feedback();
        feedback.setAdvertiser(advertiser);
        feedback.setLessor(lessor);
        feedback.setCampaign(campaign);
        feedback.setBooking(booking);
        feedback.setStatus("opened");
        feedback.setType(feedbackDTO.getType());
        feedback.setScore(feedbackDTO.getScore());
        feedback.setComment(feedbackDTO.getComment());

        feedbackRepository.save(feedback);

        lessor.setRatingAvg(calculateAverage(lessor));
        lessorRepository.save(lessor);
    }

    public void updateFeedback(Integer id , FeedbackDTO feedbackDTO){
        Feedback feedback1 = feedbackRepository.findFeedbackById(id);
        if (feedback1 == null){
            throw new ApiException("Feedback with id " + id + " not found");
        }
        feedback1.setType(feedbackDTO.getType());
        feedback1.setScore(feedbackDTO.getScore());
        feedback1.setComment(feedbackDTO.getComment());
        feedbackRepository.save(feedback1);
    }

    public void deleteFeedback(Integer id){
        Feedback feedback = feedbackRepository.findFeedbackById(id);
        if (feedback == null){
            throw new ApiException("Feedback with id " + id + " not found");
        }
        feedbackRepository.delete(feedback);
    }

    public Double calculateAverage(Lessor lessor){
        double sum = 0;
        for (Feedback f : lessor.getFeedbacks()){
            sum+= f.getScore();
        }
        return sum / lessor.getFeedbacks().size();
    }

    public List<Feedback> getOpenedFeedbacks(){
        return feedbackRepository.getOpenedFeedbacks();
    }


    public void closingFeedback(Integer id, Mail mail){
        Feedback feedback = feedbackRepository.findFeedbackById(id);

        if (feedback == null){
            throw new ApiException("feedback not found");
        }

        if (!feedback.getStatus().equalsIgnoreCase("opened")){
            throw new ApiException("feedback is closed");
        }

        feedback.setStatus("closed");

        mailService.sendWithoutAttachment(mail);
    }

    public List<Feedback> getClosedFeedback(){
        return feedbackRepository.getClosedFeedbacks();
    }
}
