package org.example.capstone3.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Advertiser;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Model.Mail;
import org.example.capstone3.Repository.AdvertiserRepository;
import org.example.capstone3.Repository.CampaignRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertiserService {

    private final AdvertiserRepository advertiserRepository;
    private final CampaignRepository campaignRepository;
    private final OtpService otpService;
    private final MailService mailService;


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper mapper = new ObjectMapper();

    @Value("${wathq.api.key}")
    private String wathqApiKey;
    public List<Advertiser> getAllAdvertiser(){
        return advertiserRepository.findAll();
    }

    public void addAdvertiser(Advertiser advertiser) throws JsonProcessingException {
        String cr = advertiser.getCrNationalNumber();
        if (cr == null || cr.isBlank()) {
            throw new ApiException("CR National Number is required");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("apiKey" , wathqApiKey);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<Void> req = new HttpEntity<>(headers);

        String url =
                 "https://api.wathq.sa/commercial-registration/fullinfo/" + cr;

        ResponseEntity<String> res = restTemplate.exchange(url, HttpMethod.GET, req, String.class);
        JsonNode root = mapper.readTree(res.getBody());

        String status = root.path("status").path("name").asText();
        if (!"نشط".equalsIgnoreCase(status)) {
            throw new ApiException("Commercial registration is not Active: " + status);
        }

        advertiserRepository.save(advertiser);
    }

    public void requestOtpForAdvertiserAction(Integer id, String actionType) {
        Advertiser advertiser = advertiserRepository.findAdvertiserById(id);
        if (advertiser == null) {
            throw new ApiException("Advertiser with id " + id + " not found");
        }
        if (advertiser.getEmail() == null || advertiser.getEmail().isEmpty()) {
            throw new ApiException("Advertiser email is not available to send OTP.");
        }

        String otp = otpService.generateOtp();
        String otpKey = "ADVERTISER_" + id + "_" + actionType.toUpperCase();
        otpService.storeOtp(otpKey, otp);

        String subject = "OTP for Advertiser " + actionType + " - Capstone3";
        String body = "Your One-Time Password for " + actionType + " operation is: " + otp + ". This OTP is valid for a short period.";
        Mail mail = new Mail();
        mail.setTo(advertiser.getEmail());
        mail.setSubject(subject);
        mail.setText(body);
        mailService.sendWithoutAttachment(mail);
    }

    public void updateAdvertiser(Integer id , Advertiser advertiser, String otp){
        String otpKey = "ADVERTISER_" + id + "_UPDATE";
        if (!otpService.verifyOtp(otpKey, otp)) {
            throw new ApiException("Invalid or expired OTP for update.");
        }

        Advertiser advertiser1 = advertiserRepository.findAdvertiserById(id);
        if (advertiser1 == null){
            throw new ApiException("Advertiser with id " + id + " not found");
        }
        advertiser1.setEmail(advertiser.getEmail());
        advertiser1.setCompanyName(advertiser.getCompanyName());
        advertiser1.setBrandName(advertiser.getBrandName());
        advertiser1.setPaymentMethod(advertiser.getPaymentMethod());
        advertiserRepository.save(advertiser1);
    }

    public void deleteAdvertiser(Integer id, String otp){
        String otpKey = "ADVERTISER_" + id + "_DELETE";
        if (!otpService.verifyOtp(otpKey, otp)) {
            throw new ApiException("Invalid or expired OTP for delete.");
        }

        Advertiser advertiser = advertiserRepository.findAdvertiserById(id);
        if (advertiser == null){
            throw new ApiException("Advertiser with id " + id + " not found");
        }
        advertiserRepository.delete(advertiser);
    }

    public List<Campaign> getAdvertiserCampaign(Integer id){
        Advertiser advertiser = advertiserRepository.findAdvertiserById(id);

        if (advertiser == null){
            throw new ApiException("Advertiser not found");
        }

        return campaignRepository.findCampaignByAdvertiser(advertiser);
    }
}
