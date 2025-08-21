package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.BookingDTO;
import org.example.capstone3.Model.Billboard;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Model.Lessor;
import org.example.capstone3.Model.Mail;
import org.example.capstone3.Repository.BillboardRepository;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.CampaignRepository;
import org.example.capstone3.Repository.LessorRepository;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final BillboardRepository billboardRepository;
    private final CampaignRepository campaignRepository;
    private final LessorRepository lessorRepository;
    private final OtpService otpService;
    private final MailService mailService;

    private final WhatsAppService whatsAppService;

    public List<Booking> getAllBooking(){
        return bookingRepository.findAll();
    }

    public void addBooking(BookingDTO bookingDTO){

        Billboard billboard = billboardRepository.findBillboardById(bookingDTO.getBillboard_id());
        Campaign campaign  = campaignRepository.findCampaignById(bookingDTO.getCampaign_id());

        Lessor lessor = lessorRepository.findLessorById(billboard.getLessor().getId());

        if (lessor == null){
            throw new ApiException("lessor not found");
        }

        if(billboard == null || campaign ==null)
            throw new ApiException("Billboard/campaign not found");




        Booking booking = new Booking();
        booking.setBillboard(billboard);
        booking.setCampaign(campaign);
        booking.setStatus("lessor_pending");
        booking.setStartDate(bookingDTO.getStartDate());
        booking.setEndDate(bookingDTO.getEndDate());
        booking.setPriceTotal(calculateTotalPricePerWeek(booking,billboard));
        bookingRepository.save(booking);



        whatsAppService.sendText(lessor.getPhoneNumber(),"you have new pending booking to check");
    }

    public void requestOtpForBookingAction(Integer id, String actionType) {
        Booking booking = bookingRepository.findBookingById(id);
        if (booking == null) {
            throw new ApiException("Booking with id " + id + " not found");
        }
        // Assuming OTP is sent to the advertiser who made the booking
        if (booking.getCampaign() == null || booking.getCampaign().getAdvertiser() == null || booking.getCampaign().getAdvertiser().getEmail() == null || booking.getCampaign().getAdvertiser().getEmail().isEmpty()) {
            throw new ApiException("Advertiser email for this booking is not available to send OTP.");
        }

        String otp = otpService.generateOtp();
        String otpKey = "BOOKING_" + id + "_" + actionType.toUpperCase();
        otpService.storeOtp(otpKey, otp);

        String subject = "OTP for Booking " + actionType + " - Capstone3";
        String body = "Your One-Time Password for " + actionType + " operation is: " + otp + ". This OTP is valid for a short period.";
        Mail mail = new Mail();
        mail.setTo(booking.getCampaign().getAdvertiser().getEmail());
        mail.setSubject(subject);
        mail.setText(body);
        mailService.sendWithoutAttachment(mail);
    }

    public void updateBooking(Integer id , BookingDTO bookingDTO, String otp){
        String otpKey = "BOOKING_" + id + "_UPDATE";
        if (!otpService.verifyOtp(otpKey, otp)) {
            throw new ApiException("Invalid or expired OTP for update.");
        }

        Booking oldBooking = bookingRepository.findBookingById(id);

        if (oldBooking == null){
            throw new ApiException("Booking with id " + id + " not found");
        }
        oldBooking.setStartDate(bookingDTO.getStartDate());
        oldBooking.setEndDate(bookingDTO.getEndDate());
        bookingRepository.save(oldBooking);
    }

    public void deleteBooking(Integer id, String otp){
        String otpKey = "BOOKING_" + id + "_DELETE";
        if (!otpService.verifyOtp(otpKey, otp)) {
            throw new ApiException("Invalid or expired OTP for delete.");
        }

        Booking booking = bookingRepository.findBookingById(id);
        if (booking == null){
            throw new ApiException("Booking with id " + id + " not found");
        }
        bookingRepository.delete(booking);
    }

    public List<Booking> findPendingBookings(Integer lessorId) {
        List<Booking> results =
                bookingRepository.findByStatusAndBillboard_Lessor_Id("lessor_pending", lessorId);

        if (results.isEmpty()) {
            throw new ApiException("No bookings found");
        }
        return results;
    }


    public void acceptBooking(Integer lessorId, Integer bookingId, String otp) {
        String otpKey = "BOOKING_" + bookingId + "_ACCEPT";
        if (!otpService.verifyOtp(otpKey, otp)) {
            throw new ApiException("Invalid or expired OTP for accept booking.");
        }

        Booking booking = bookingRepository.findByIdAndBillboard_Lessor_Id(bookingId, lessorId);
        Lessor lessor = lessorRepository.findLessorById(lessorId);
        if(lessor == null)
            throw new ApiException("Lessor not found");

        if(booking == null)
            throw new ApiException("Booking not found or not owned by you");

        if (!"lessor_pending".equals(booking.getStatus())) {
            throw new ApiException("Only pending bookings can be accepted");
        }
        lessor.setRentCount(lessor.getRentCount() + 1);

        booking.setStatus("accepted_payment_pending");
        booking.setAcceptedAt(OffsetDateTime.now());

        lessorRepository.save(lessor);
        bookingRepository.save(booking);
    }

    //Helper Method

    public double calculateTotalPricePerWeek(Booking booking, Billboard billboard) {
        if (booking == null || billboard == null) {
            throw new ApiException("booking and billboard are required");
        }

        LocalDate start = Objects.requireNonNull(booking.getStartDate(), "startDate is required");
        LocalDate end   = Objects.requireNonNull(booking.getEndDate(),   "endDate is required");
        if (end.isBefore(start)) {
            throw new ApiException("endDate must be on or after startDate");
        }

        double weeklyPrice = Objects.requireNonNull(billboard.getBasePricePerWeek(), "basePricePerWeek is required");
        if (weeklyPrice <= 0) {
            throw new ApiException("basePricePerWeek must be > 0");
        }

        long daysInclusive = ChronoUnit.DAYS.between(start, end) + 1;  // inclusive range
        long weeksToBill = (long) Math.ceil(daysInclusive / 7.0);      // charge started weeks

        return weeksToBill * weeklyPrice;
    }


    @Scheduled(cron = "0 0 * * * ?")
    public void updateBookingStatusBasedOnEndDate() {
        List<Booking> bookings = bookingRepository.findAll();
        LocalDate today = LocalDate.now();

        for (Booking booking : bookings) {
            if (booking.getEndDate() != null && booking.getEndDate().isBefore(today)) {
                if (!"completed".equals(booking.getStatus()) && !"cancelled".equals(booking.getStatus())) {
                    booking.setStatus("completed");
                    bookingRepository.save(booking);
                    logger.info("Booking {} status updated to completed.", booking.getId());
                }
            }
        }
    }
}
