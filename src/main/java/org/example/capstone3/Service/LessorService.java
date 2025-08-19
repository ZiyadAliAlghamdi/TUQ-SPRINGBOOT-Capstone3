package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Lessor;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.LessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessorService {

    private final LessorRepository lessorRepository;
    public List<Lessor> getAllLessor(){
        return lessorRepository.findAll();
    }

    public void addLessor(Lessor lessor){
        lessorRepository.save(lessor);
    }

    public void updateLessor(Integer id , Lessor lessor){
        Lessor oldLessor = lessorRepository.findLessorById(id);
        if (oldLessor == null){
            throw new ApiException("Lessor with id " + id + " not found");
        }
        oldLessor.setBusinessName(lessor.getBusinessName());
        oldLessor.setEmail(lessor.getEmail());
        oldLessor.setOperatingRegions(lessor.getOperatingRegions());
        oldLessor.setContentRestrictions(lessor.getContentRestrictions());
        oldLessor.setRatingAvg(lessor.getRatingAvg());
        lessorRepository.save(oldLessor);
    }

    public void deleteLessor(Integer id){
        Lessor lessor = lessorRepository.findLessorById(id);
        if (lessor == null){
            throw new ApiException("Lessor with id " + id + " not found");
        }
        lessorRepository.delete(lessor);
    }


}
