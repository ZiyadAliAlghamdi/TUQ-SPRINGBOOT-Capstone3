package org.example.capstone3.Service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.InvoiceDTO;
import org.example.capstone3.Model.Advertiser;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Invoice;
import org.example.capstone3.Repository.BookingRepository;
import org.example.capstone3.Repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final BookingRepository bookingRepository;

    private final MailService mailService;
    private final InvoicePdfBuilder invoicePdfBuilder;

    @Value("${moyasar.api.key}")
    private String apiKey;

    private static final String MOYASAR_API_URL = "https://api.moyasar.com/v1/payments";



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




    public ResponseEntity<String> processPayment(InvoiceDTO invoiceDTO){

        Booking booking = bookingRepository.findBookingById(invoiceDTO.getBookingId());

        if (booking ==  null){
            throw new ApiException("booking is not found");
        }

        if (!booking.getStatus().equalsIgnoreCase("accepted_payment_pending")){
            throw new ApiException("cannot continue payment, booking status: "+booking.getStatus());
        }

        String callbackUrl = "http://localhost:8080/api/v1/invoice/callback";


        //create the body
        String requestBody =String.format("source[type]=card&source[name]=%s&source[number]=%s&source[cvc]=%s&source[month]=%s&source[year]=%s&amount=%d&currency=%s&callback_url=%s",
                invoiceDTO.getCardName(),
                invoiceDTO.getCardNumber(),
                invoiceDTO.getCardCvc(),
                invoiceDTO.getCardMonth(),
                invoiceDTO.getCardYear(),
                (int) (booking.getPriceTotal() * 100),
                "SAR",
                callbackUrl);


        //set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(apiKey,"");
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        //send the request

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<JsonNode> response = restTemplate.exchange(
                MOYASAR_API_URL,
                HttpMethod.POST,
                entity,
                JsonNode.class);

        // Parse JSON response
        JsonNode root = response.getBody();
        String status = root.path("status").asText();          // e.g. "paid", "failed", "initiated"
        String paymentId = root.path("id").asText(null);
        String brand = root.path("source").path("company").asText(null); // depends on Moyasar payload
        String last4 = root.path("source").path("last4").asText(null);
        String masked = (last4 == null || last4.isEmpty()) ? null : ("**** **** **** " + last4);


        Invoice inv = new Invoice();
        inv.setBooking(booking);
        inv.setCardName(invoiceDTO.getCardName());
        inv.setCardNumber(invoiceDTO.getCardNumber());
        inv.setCardCvc(invoiceDTO.getCardCvc());
        inv.setCardMonth(invoiceDTO.getCardMonth());
        inv.setCardYear(invoiceDTO.getCardYear());
        inv.setAmount(booking.getPriceTotal());
        inv.setCurrency("SAR");
        inv.setDueDate(LocalDate.now());
        inv.setStatus(status);
        inv.setPaymentId(paymentId);

        invoiceRepository.save(inv);



        Advertiser adv = booking.getCampaign().getAdvertiser();
        String from = booking.getBillboard().getLessor().getEmail();
        String to = adv.getEmail();



        byte[] pdf = invoicePdfBuilder.build(
                inv,
                adv,
                String.valueOf(booking.getId()),
                paymentId,
                brand,
                masked
        );


        String subject = "Payment Receipt - Booking #" + booking.getId();
        String body = """
        <p>Dear %s,</p>
        <p>Thank you. We have received your payment.</p>
        <ul>
          <li><b>Booking Ref:</b> %s</li>
          <li><b>Amount:</b> %.2f %s</li>
          <li><b>Status:</b> %s</li>
          <li><b>Payment ID:</b> %s</li>
        </ul>
        <p>please continue your payment process here: <a href=\"%%s\">%%s</a></p>
        <p>The receipt PDF is attached.</p>
        """.formatted(adv.getCompanyName(),
                booking.getId(),
                inv.getAmount(), inv.getCurrency(),
                inv.getStatus(),
                paymentId == null ? "-" : paymentId,
                root.path("source").path("transaction_url").asText(null),
                root.path("source").path("transaction_url").asText(null));

        mailService.sendWithAttachment(from,
                to,
                subject,
                body,
                pdf,
                "receipt-" + (paymentId == null ? booking.getId() : paymentId) + ".pdf"
        );

        return ResponseEntity.status(response.getStatusCode()).body(response.getBody().toString());
    }



    public void handlePaymentCallback(String id, String status, String amount, String message) {
        Invoice invoice = invoiceRepository.findInvoiceByPaymentId(id);
        if (invoice == null) {
            throw new ApiException("Invoice not found");
        }

        if (status.equalsIgnoreCase("paid")) {
            Booking booking = invoice.getBooking();
            booking.setStatus("approved");
            bookingRepository.save(booking);
            invoice.setStatus("paid");
            invoiceRepository.save(invoice);
        }
    }
}
