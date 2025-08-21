package org.example.capstone3.Repository;

import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Integer> {
    Feedback findFeedbackById(Integer id);

    boolean existsByBooking_IdAndAdvertiser_Id(Integer bookingId, Integer advertiserId);

    @Query("select f from Feedback f where f.status ='opened'")
    List<Feedback> getOpenedFeedbacks();

    @Query("select f from Feedback f where f.status = 'closed'")
    List<Feedback> getClosedFeedbacks();

}
