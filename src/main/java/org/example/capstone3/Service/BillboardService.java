package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.BillboardDTO;
import org.example.capstone3.Model.Billboard;
import org.example.capstone3.Model.Lessor;
import org.example.capstone3.Repository.BillboardRepository;
import org.example.capstone3.Repository.LessorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillboardService {

    private final BillboardRepository billboardRepository;
    private final LessorRepository lessorRepository;
    public List<Billboard> getAllBillboard(){
        return billboardRepository.findAll();
    }

    public void addBillboard(BillboardDTO billboardDTO){
        Lessor lessor = lessorRepository.findLessorById(billboardDTO.getLessor_id());
        if(lessor == null)
            throw new ApiException("Lessor not found");
        Billboard billboard = new Billboard();
        billboard.setLessor(lessor);
        billboard.setTitle(billboardDTO.getTitle());
        billboard.setDistrict(billboardDTO.getDistrict());
        billboard.setType(billboardDTO.getType());
        billboard.setWidth(billboardDTO.getWidth());
        billboard.setHeight(billboardDTO.getHeight());
        billboard.setAvailabilityStatus(billboardDTO.getAvailabilityStatus());
        billboard.setBasePricePerWeek(billboardDTO.getBasePricePerWeek());

        billboardRepository.save(billboard);
    }

    public void updateBillboard(Integer id,BillboardDTO billboardDTO){
        Lessor lessor = lessorRepository.findLessorById(billboardDTO.getLessor_id());
        if(lessor == null)
            throw new ApiException("Lessor not found");
        Billboard oldBillboard = billboardRepository.findBillboardById(id);
        if (oldBillboard == null){
            throw new ApiException("Billboard with id " + id + " not found");
        }
        oldBillboard.setLessor(lessor);
        oldBillboard.setTitle(billboardDTO.getTitle());
        oldBillboard.setDistrict(billboardDTO.getDistrict());
        oldBillboard.setType(billboardDTO.getType());
        oldBillboard.setWidth(billboardDTO.getWidth());
        oldBillboard.setHeight(billboardDTO.getHeight());
        oldBillboard.setAvailabilityStatus(billboardDTO.getAvailabilityStatus());
        oldBillboard.setBasePricePerWeek(billboardDTO.getBasePricePerWeek());
        billboardRepository.save(oldBillboard);
    }

    public void deleteBillboard(Integer lessor_id,Integer id){
        Lessor lessor = lessorRepository.findLessorById(lessor_id);
        if(lessor == null)
            throw new ApiException("Lessor not found");

        Billboard billboard = billboardRepository.findBillboardById(id);
        if (billboard == null){
            throw new ApiException("Billboard with id " + id + " not found");
        }
        if(!billboard.getLessor().equals(lessor))
            throw new ApiException("Billboard Is Not For given Lessor");

        billboardRepository.delete(billboard);
    }


    //EXTRA:

    public List<Billboard>  findBillboardsByDistrict(String district){
        return billboardRepository.findBillboardsByDistrict(district);
    }


    public List<Billboard> findBillboardsByHeightAndWidth(Double height,Double width){
        return billboardRepository.findBillboardsByHeightAndWidth(height,width);
    }

    public List<Billboard> findBillboardsByLessor_RatingAvg(){
    return billboardRepository.findAllByOrderByLessor_RatingAvgDesc();
    }

    public List<Billboard> findBillboardsByAvailabilityStatus(String availabilityStatus){
        return billboardRepository.findBillboardsByAvailabilityStatus(availabilityStatus);
    }






}
