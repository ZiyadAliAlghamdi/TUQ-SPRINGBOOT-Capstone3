package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Advertiser;
import org.example.capstone3.Repository.AdvertiserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertiserService {

    private final AdvertiserRepository advertiserRepository;

    public List<Advertiser> getAllAdvertiser(){
        return advertiserRepository.findAll();
    }

    public void addAdvertiser(Advertiser advertiser){
        advertiserRepository.save(advertiser);
    }

    public void updateAdvertiser(Integer id , Advertiser advertiser){
        Advertiser advertiser1 = advertiserRepository.findAdvertiserById(id);
        if (advertiser1 == null){
            throw new ApiException("Advertiser with id " + id + " not found");
        }
        advertiser1.setCompanyName(advertiser.getCompanyName());
        advertiser1.setBrandName(advertiser.getBrandName());
        advertiser1.setPaymentMethod(advertiser.getPaymentMethod());
        advertiser1.setNotes(advertiser.getNotes());
        advertiserRepository.save(advertiser1);
    }

    public void deleteAdvertiser(Integer id){
        Advertiser advertiser = advertiserRepository.findAdvertiserById(id);
        if (advertiser == null){
            throw new ApiException("Advertiser with id " + id + " not found");
        }
        advertiserRepository.delete(advertiser);
    }
}
