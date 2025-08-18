package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Billboard;
import org.example.capstone3.Repository.BillboardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BillboardService {

    private final BillboardRepository billboardRepository;

    public List<Billboard> getAllBillboard(){
        return billboardRepository.findAll();
    }

    public void addBillboard(Billboard billboard){
        billboardRepository.save(billboard);
    }

    public void updateBillboard(Integer id , Billboard billboard){
        Billboard billboard1 = billboardRepository.findBillboardById(id);
        if (billboard1 == null){
            throw new ApiException("Billboard with id " + id + " not found");
        }
        billboard1.setTitle(billboard.getTitle());
        billboard1.setAddress(billboard.getAddress());
        billboard1.setLat(billboard.getLat());
        billboard1.setLng(billboard.getLng());
        billboard1.setType(billboard.getType());
        billboard1.setWidth(billboard.getWidth());
        billboard1.setHeight(billboard.getHeight());
        billboard1.setAvailabilityStatus(billboard.getAvailabilityStatus());
        billboard1.setBasePricePerWeek(billboard.getBasePricePerWeek());
        billboard1.setRatingAvg(billboard.getRatingAvg());
        billboard1.setRatingCount(billboard.getRatingCount());
        billboardRepository.save(billboard1);
    }

    public void deleteBillboard(Integer id){
        Billboard billboard = billboardRepository.findBillboardById(id);
        if (billboard == null){
            throw new ApiException("Billboard with id " + id + " not found");
        }
        billboardRepository.delete(billboard);
    }
}
