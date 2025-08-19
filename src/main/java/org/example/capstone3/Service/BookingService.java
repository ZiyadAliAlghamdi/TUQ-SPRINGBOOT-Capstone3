package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.BookingDTO;
import org.example.capstone3.Model.Billboard;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Repository.BillboardRepository;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.CampaignRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final BillboardRepository billboardRepository;
    private final CampaignRepository campaignRepository;
    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    public void addBooking(BookingDTO bookingDTO){
        Billboard billboard = billboardRepository.findBillboardById(bookingDTO.getBillboard_id());
        Campaign campaign  = campaignRepository.findCampaignById(bookingDTO.getCampaign_id());
        if(billboard == null || campaign ==null)
            throw new ApiException("Billboard/campaign not found");
        Booking booking = new Booking();
        booking.setBillboard(billboard);
        booking.setCampaign(campaign);
        booking.setStatus("pending"); // todo: Need to talk about it
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setPriceTotal(calculateTotalPricePerWeek(booking,billboard));
        bookingRepository.save(booking);
    }

    public void updateBooking(Integer id , BookingDTO booking){

        Booking oldBooking = bookingRepository.findBookingById(id);

        if (oldBooking == null){
            throw new ApiException("Booking with id " + id + " not found");
        }
        oldBooking.setStartDate(booking.getStartDate());
        oldBooking.setEndDate(booking.getEndDate());
        oldBooking.setPriceTotal(booking.getPriceTotal());
        oldBooking.setStatus(booking.getStatus());
        bookingRepository.save(oldBooking);
    }

    public void deleteBooking(Integer id){
        Booking booking = bookingRepository.findBookingById(id);
        if (booking == null){
            throw new ApiException("Booking with id " + id + " not found");
        }
        bookingRepository.delete(booking);
    }

    public Double calculateTotalPricePerWeek(Booking booking, Billboard billboard) {
        if (booking == null || billboard == null) {
            throw new ApiException("startDate, endDate, and billboard are required");
        }
        if (booking.getEndDate().isBefore(booking.getStartDate())) {
            throw new ApiException("endDate must be on or after startDate");
        }
        long weeks = ChronoUnit.WEEKS.between(booking.getStartDate(), booking.getEndDate());

        return weeks * billboard.getBasePricePerWeek();
    }
}
