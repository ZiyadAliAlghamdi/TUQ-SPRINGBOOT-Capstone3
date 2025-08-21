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
import org.springframework.ai.openai.OpenAiChatModel;
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
    private final OpenAiChatModel ai;

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

    //Helper methods

    public String competitorAnalysis(Integer advertiserId) {
        Advertiser adv = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new ApiException("Advertiser not found"));
        if (!adv.isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        String prompt = """
    أنت محلل تسويق متخصص في السوق السعودي للوحات الإعلانية.

    اسم الشركة: %s
    اسم العلامة التجارية: %s

    التزم بالتالي:
    - اكتب بالعربية الفصحى.
    - أجب فقط بنقاط تبدأ بعلامة "- ".
    - لا تستخدم Markdown أو مقدمات.
    - اذكر: 
      - نقاط قوة العلامة التجارية (3 نقاط).
      - نقاط ضعف محتملة (3 نقاط).
      - توصيات لمواجهة المنافسة (4 نقاط).
    """.formatted(adv.getCompanyName(), adv.getBrandName());

        return ai.call(prompt);
    }
    public String suggestCampaignIdeas(Integer advertiserId) {
        Advertiser adv = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new ApiException("Advertiser not found"));
        if (!adv.isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        String districts = adv.getCampaigns().stream()
                .map(Campaign::getDistrict)
                .distinct()
                .reduce("", (a, b) -> a + ", " + b);

        String prompt = """
    أنت خبير إعلانات خارجية في السعودية.

    العلامة التجارية: %s
    الأحياء المستهدفة: %s

    المطلوب:
    - اكتب بالعربية الفصحى.
    - لا مقدمات ولا Markdown.
    - قدم 5 أفكار إعلانية مبتكرة للوحات في تلك الأحياء (كل فكرة نقطة جديدة).
    """.formatted(adv.getBrandName(), districts);

        return ai.call(prompt);
    }
    public String summarizeFeedback(Integer advertiserId) {
        Advertiser adv = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new ApiException("Advertiser not found"));
        if (!adv.isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        StringBuilder feedbackBuilder = new StringBuilder();
        adv.getFeedbacks().forEach(f -> feedbackBuilder
                .append("التصنيف: ").append(f.getType())
                .append(" | التقييم: ").append(f.getScore())
                .append(" | التعليق: ").append(f.getComment())
                .append("\n"));

        String prompt = """
    أنت محلل آراء العملاء.

    بيانات التعليقات:
    %s

    التزم بالتالي:
    - اكتب نقاط موجزة بالعربية الفصحى.
    - لا مقدمات ولا Markdown.
    - قدم:
      - أبرز 3 نقاط إيجابية (استنادًا إلى التعليقات والتقييمات).
      - أبرز 3 ملاحظات سلبية.
      - 3 توصيات عملية لتحسين الحملات.
    """.formatted(feedbackBuilder.toString());

        return ai.call(prompt);
    }

    public String monthlyStrategy(Integer advertiserId) {
        Advertiser adv = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new ApiException("Advertiser not found"));
        if (!adv.isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        String prompt = """
    أنت خبير استراتيجيات إعلانات.

    الشركة: %s
    العلامة التجارية: %s

    المطلوب:
    - بالعربية الفصحى.
    - لا مقدمات ولا Markdown.
    - خطّة إعلانية شهرية مقسّمة:
      - الأسبوع 1 (نقطتان).
      - الأسبوع 2 (نقطتان).
      - الأسبوع 3 (نقطتان).
      - الأسبوع 4 (نقطتان).
    """.formatted(adv.getCompanyName(), adv.getBrandName());

        return ai.call(prompt);
    }


    public String complianceCheck(Integer advertiserId) {
        Advertiser adv = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new ApiException("Advertiser not found"));
        if (!adv.isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        String prompt = """
    أنت مستشار قانوني في مجال الإعلانات الخارجية في السعودية.

    الشركة: %s
    العلامة التجارية: %s

    المطلوب:
    - اكتب نقاط بالعربية الفصحى.
    - لا مقدمات ولا Markdown.
    - اذكر:
      - 3 مخاطر محتملة تتعلق بالامتثال.
      - 3 توصيات لتجنّبها.
    """.formatted(adv.getCompanyName(), adv.getBrandName());

        return ai.call(prompt);
    }

    public String adviseBillboardType(Integer advertiserId) {
        Advertiser adv = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new ApiException("Advertiser not found"));
        if (!adv.isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        String prompt = """
    أنت خبير إعلانات خارجية متخصص في اختيار أنواع اللوحات الإعلانية في السعودية.

    الشركة: %s
    العلامة التجارية: %s

    المطلوب:
    - اكتب نقاط بالعربية الفصحى.
    - لا مقدمات ولا Markdown.
    - قدم:
      - 3 أنواع لوحات إعلانية مناسبة لأهداف المعلن.
      - المميزات الأساسية لكل نوع.
      - متى يُفضَّل استخدام كل نوع.
    """.formatted(adv.getCompanyName(), adv.getBrandName());

        return ai.call(prompt);
    }

    public String audienceDemographicsAnalysis(Integer advertiserId) {
        Advertiser adv = advertiserRepository.findById(advertiserId)
                .orElseThrow(() -> new ApiException("Advertiser not found"));
        if (!adv.isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        String prompt = """
    أنت محلل تسويق متخصص في الجمهور المستهدف للإعلانات الخارجية في السعودية.

    الشركة: %s
    العلامة التجارية: %s

    المطلوب:
    - اكتب نقاط بالعربية الفصحى.
    - لا مقدمات ولا Markdown.
    - اذكر:
      - 3 فئات عمرية أو شرائح ديموغرافية مناسبة للحملة.
      - 3 أنماط سلوك استهلاكي لهذه الشرائح.
      - 3 توصيات عملية للوصول بشكل أفضل إلى هذه الفئات.
    """.formatted(adv.getCompanyName(), adv.getBrandName());

        return ai.call(prompt);
    }
}
