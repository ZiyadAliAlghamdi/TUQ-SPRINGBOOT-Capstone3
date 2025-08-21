package org.example.capstone3.Repository;

import org.example.capstone3.Model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findBookingById(Integer id);

    List<Booking> findByStatusAndBillboard_Lessor_Id(String status, Integer LessorId);

    Booking findByIdAndBillboard_Lessor_Id(Integer bookingId, Integer lessorId);

    @Query("select b from Booking  b where b.status ='lessor_pending'")
    List<Booking> findLessorPendingBookings();


}
