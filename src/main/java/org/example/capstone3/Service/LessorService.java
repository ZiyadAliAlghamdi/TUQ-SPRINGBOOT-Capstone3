package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Lessor;
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
        Lessor lessor1 = lessorRepository.findLessorById(id);
        if (lessor1 == null){
            throw new ApiException("Lessor with id " + id + " not found");
        }
        lessor1.setBusinessName(lessor.getBusinessName());
        lessor1.setEmail(lessor.getEmail());
        lessor1.setOperatingRegions(lessor.getOperatingRegions());
        lessor1.setContentRestrictions(lessor.getContentRestrictions());
        lessor1.setRatingAvg(lessor.getRatingAvg());
        lessor1.setRentCount(lessor.getRentCount());
        lessorRepository.save(lessor1);
    }

    public void deleteLessor(Integer id){
        Lessor lessor = lessorRepository.findLessorById(id);
        if (lessor == null){
            throw new ApiException("Lessor with id " + id + " not found");
        }
        lessorRepository.delete(lessor);
    }
}
