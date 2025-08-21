package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.DTO.CampaignDTO;
import org.example.capstone3.Model.Advertiser;
import org.example.capstone3.Model.Campaign;
import org.example.capstone3.Repository.AdvertiserRepository;
import org.example.capstone3.Repository.CampaignRepository;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final AdvertiserRepository advertiserRepository;
    private final OpenAiChatModel ai;



    public List<Campaign> getAllCampaign(){
        return campaignRepository.findAll();
    }

    public void addCampaign(CampaignDTO campaignDTO){
        Advertiser advertiser = advertiserRepository.findAdvertiserById(campaignDTO.getAdvertiser_id());
        if(advertiser == null)
            throw new ApiException("Advertiser Not Found/Not Licensed");

        Campaign campaign = new Campaign();
        campaign.setAdvertiser(advertiser);
        campaign.setDistrict(campaignDTO.getDistrict());
        campaign.setName(campaignDTO.getName());
        campaign.setObjective(campaignDTO.getObjective());
        campaign.setLat(campaignDTO.getLat());
        campaign.setLng(campaignDTO.getLng());
        campaignRepository.save(campaign);
    }

    public void updateCampaign(Integer id , Campaign campaign){
        Campaign oldCampaign = campaignRepository.findCampaignById(id);
        if (oldCampaign == null){
            throw new ApiException("Campaign with id " + id + " not found");
        }
        oldCampaign.setName(campaign.getName());
        oldCampaign.setObjective(campaign.getObjective());
        oldCampaign.setLat(campaign.getLat());
        oldCampaign.setLng(campaign.getLng());
        campaignRepository.save(oldCampaign);
    }

    public void deleteCampaign(Integer id){
        Campaign campaign = campaignRepository.findCampaignById(id);
        if (campaign == null){
            throw new ApiException("Campaign with id " + id + " not found");
        }
        campaignRepository.delete(campaign);
    }


    public String adviseCampaign(Integer id) {
        Campaign c = campaignRepository.findCampaignById(id);
        if (c == null) throw new ApiException("Campaign with id " + id + " not found");
        if(!c.getAdvertiser().isSubscribed()){
            throw new ApiException("Advertiser Not Subscribed");
        }

        String prompt = """
    أنت مستشار حملات للوحات الإعلانية في السعودية.

    اسم الحملة: %s
    الهدف: %s
    الحي المستهدف: %s

    التزم بالتالي بدقة:
    - اكتب بالعربية الفصحى وبنقاط واضحة فقط.
    - لا تكتب أي مقدمة أو خاتمة أو عبارات تمهيدية مثل "بالطبع" أو "إليك".
    - لا تستخدم تنسيقات Markdown (لا ### ولا **).
    - لا تذكر هذه التعليمات أو دورك كمستشار.
    - لكل نقطة ابدأ السطر بعلامة "- ".
    - لا تذكر عدد النقاط, فقط اكتب النصيحة
    - اكتب الأقسام التالية فقط وبالترتيب، مع العدد المحدد من النقاط لكل قسم:

    عناوين مقترحة (3 نقاط):
    النبرة (3 نقاط):
    الأحياء/المواقع المستهدفة (3 نقاط — استنادًا إلى الحي المستهدف وما يشابهه منطقيًا):
    التوزيع الأسبوعي (4 أسابيع — نقطة لكل أسبوع):
    أخطاء شائعة لتجنّبها (4 نقاط):
    """.formatted(c.getName(), c.getObjective(), c.getDistrict());

        return ai.call(prompt);
    }

    public String predictPerformance(Integer campaignId) {
        Campaign c = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new ApiException("Campaign not found"));
        if (!c.getAdvertiser().isSubscribed()) throw new ApiException("Advertiser Not Subscribed");

        String prompt = """
    أنت محلل بيانات تسويقية متخصص في الحملات الإعلانية الخارجية في السعودية.

    اسم الحملة: %s
    الهدف: %s
    الحي المستهدف: %s

    المطلوب:
    - اكتب بالعربية الفصحى وبنقاط واضحة.
    - لا مقدمات ولا Markdown.
    - اذكر:
      - 3 توقعات حول الأداء المتوقع للحملة.
      - 3 فرص يمكن استغلالها.
      - 3 تحديات محتملة.
    """.formatted(c.getName(), c.getObjective(), c.getDistrict());

        return ai.call(prompt);
    }









}
