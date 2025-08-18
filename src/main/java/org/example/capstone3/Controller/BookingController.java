package org.example.capstone3.Controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.Booking;
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
    public ResponseEntity<?> addBooking(@RequestBody @Valid Booking booking){
        bookingService.addBooking(booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBooking(@PathVariable Integer id, @RequestBody @Valid Booking booking){
        bookingService.updateBooking(id, booking);
        return ResponseEntity.status(200).body(new ApiResponse("Booking updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBooking(@PathVariable Integer id){
        bookingService.deleteBooking(id);
        return ResponseEntity.status(200).body(new ApiResponse("Booking deleted successfully"));
    }
}
