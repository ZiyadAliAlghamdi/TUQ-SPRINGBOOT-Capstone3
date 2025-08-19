package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments";


    public ResponseEntity<String> processPayment(Payment payment){


        String callbackUrl = "https://moyasar.com/thanks";


        //create the body
        String requestBody =String.format("source[type]=card&source[name]=%s&source[number]=%s&source[cvc]=%s&source[month]=%s&source[year]=%s&amount=%d&currency=%s&callback_url=%s",
                payment.getName(),
                payment.getNumber(),
                payment.getCvc(),
                payment.getMonth(),
                payment.getYear(),
                (int) (payment.getAmount() * 100),
                payment.getCurrency(),
                callbackUrl);


        //set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");    //todo: check password
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        //send the request

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(MOYASAR_API_URL,
                HttpMethod.POST,
                entity,
                String.class);

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }


    public String getPaymentStatus(String paymentId){
        HttpHeaders headers = new HttpHeaders();

        //prepare headers
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_JSON);

        //create the request entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        //call the api

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response = restTemplate.exchange(
                MOYASAR_API_URL + "/" + paymentId ,HttpMethod.GET, entity, String.class
        );
        //return the response
        return response.getBody();
    }

}
