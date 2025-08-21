package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Billboard;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Invoice;
import org.example.capstone3.Model.Lessor;
import org.example.capstone3.Repository.BillboardRepository;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.InvoiceRepository;
import org.example.capstone3.Repository.LessorRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessorService {

    private final LessorRepository lessorRepository;
    private final BookingRepository bookingRepository;
    private final BillboardRepository billboardRepository;
    private final InvoiceRepository invoiceRepository;

    private final WhatsAppService whatsAppService;


    public List<Lessor> getAllLessor(){
        return lessorRepository.findAll();
    }

    public void addLessor(Lessor lessor){
        lessor.setRentCount(0);
        lessor.setRatingAvg(0.0);
        lessorRepository.save(lessor);
    }

    public void updateLessor(Integer id , Lessor lessor){
        Lessor oldLessor = lessorRepository.findLessorById(id);
        if (oldLessor == null){
            throw new ApiException("Lessor with id " + id + " not found");
        }
        oldLessor.setBusinessName(lessor.getBusinessName());
        oldLessor.setEmail(lessor.getEmail());
        oldLessor.setPhoneNumber(lessor.getPhoneNumber());
        oldLessor.setOperatingRegions(lessor.getOperatingRegions());
        oldLessor.setContentRestrictions(lessor.getContentRestrictions());
        lessorRepository.save(oldLessor);
    }

    public void deleteLessor(Integer id){
        Lessor lessor = lessorRepository.findLessorById(id);
        if (lessor == null){
            throw new ApiException("Lessor with id " + id + " not found");
        }
        lessorRepository.delete(lessor);
    }


    public List<Billboard> getLessorBillboards(Integer id){
        Lessor lessor = lessorRepository.findLessorById(id);

        if (lessor == null){
            throw new ApiException("lessor not found");
        }

        return billboardRepository.findBillboardByLessor(lessor.getId());
    }

    public List<Invoice> getLessorInvoices(Integer lessorId){
        Lessor lessor = lessorRepository.findLessorById(lessorId);

        if (lessor == null){
            throw new ApiException("lessor not found");
        }

        return invoiceRepository.findInvoiceByLessor(lessor.getId());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void remindPendingBooking(){
        List<Lessor> lessors = lessorRepository.findAll();
        for (Lessor lessor : lessors) {
            List<Booking> pendingBookings = bookingRepository.findByStatusAndBillboard_Lessor_Id("lessor_pending",lessor.getId());
            int pendingBookingsCount = pendingBookings.size();
            if (pendingBookingsCount > 0) {
                StringBuilder message = new StringBuilder("You have " + pendingBookingsCount + " pending bookings:\n");
                for (Booking booking : pendingBookings) {
                    message.append("  - Booking ID: ").append(booking.getId())
                            .append(", Billboard ID: ").append(booking.getBillboard().getId())
                            .append(", Start Date: ").append(booking.getStartDate())
                            .append(", End Date: ").append(booking.getEndDate()).append("\n");
                }
                whatsAppService.sendText(lessor.getPhoneNumber(), message.toString());
            }
        }
    }

}