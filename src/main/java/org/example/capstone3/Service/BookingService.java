package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;

    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    public void addBooking(Booking booking){
        bookingRepository.save(booking);
    }

    public void updateBooking(Integer id , Booking booking){
        Booking booking1 = bookingRepository.findBookingById(id);
        if (booking1 == null){
            throw new ApiException("Booking with id " + id + " not found");
        }
        booking1.setStartDate(booking.getStartDate());
        booking1.setEndDate(booking.getEndDate());
        booking1.setPriceTotal(booking.getPriceTotal());
        booking1.setStatus(booking.getStatus());
        booking1.setCreatedAt(booking.getCreatedAt());
        bookingRepository.save(booking1);
    }

    public void deleteBooking(Integer id){
        Booking booking = bookingRepository.findBookingById(id);
        if (booking == null){
            throw new ApiException("Booking with id " + id + " not found");
        }
        bookingRepository.delete(booking);
    }
}
