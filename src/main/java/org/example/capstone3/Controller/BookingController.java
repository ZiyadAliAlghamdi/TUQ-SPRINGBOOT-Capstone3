package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.DTO.BookingDTO;
import org.example.capstone3.Service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllBooking(){
        return ResponseEntity.status(200).body(bookingService.getAllBooking());
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBooking(@RequestBody @Valid BookingDTO booking){
        bookingService.addBooking(booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking added successfully"));
    }

    @PostMapping("/request-otp/{id}/{actionType}")
    public ResponseEntity<?> requestBookingOtp(@PathVariable Integer id, @PathVariable String actionType) {
        bookingService.requestOtpForBookingAction(id, actionType);
        return ResponseEntity.status(200).body(new ApiResponse("OTP requested successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer id, @RequestBody @Valid BookingDTO booking, @RequestParam String otp){
        bookingService.updateBooking(id, booking, otp);
        return ResponseEntity.status(200).body(new ApiResponse("Booking updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer id, @RequestParam String otp){
        bookingService.deleteBooking(id, otp);
        return ResponseEntity.status(200).body(new ApiResponse("Booking deleted successfully"));
    }

    @GetMapping("/{lessorId}/bookings/pending")
    public ResponseEntity<?> getPendingBookings(@PathVariable Integer lessorId) {
        return ResponseEntity.status(200).body(bookingService.findPendingBookings(lessorId));
    }

    @PutMapping("/{lessorId}/bookings/{bookingId}/accept")
    public ResponseEntity<?> acceptBooking(@PathVariable Integer lessorId, @PathVariable Integer bookingId, @RequestParam String otp) {
        bookingService.acceptBooking(lessorId, bookingId, otp);
        return ResponseEntity.status(200).body(new ApiResponse("Booking Accepted"));
    }




}
