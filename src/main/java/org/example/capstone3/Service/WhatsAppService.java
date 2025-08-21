package org.example.capstone3.Service;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.example.capstone3.Api.ApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

//todo: need to try tomorrow
@Service
public class WhatsAppService {

    @Value("${ultramsg.base-url}")
    private String baseUrl;

    @Value("${ultramsg.instance-id}")
    private String instanceId;

    @Value("${ultramsg.token}")
    private String token;



    public void sendText(String to, String body) {
        String normalizedTo = normalizeTo(to);
        String url = api("messages/chat");
        try {
            HttpResponse<String> res = Unirest.post(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", token)
                    .field("to", normalizedTo)
                    .field("body", body)
                    .asString();

            if (res.getStatus() < 200 || res.getStatus() >= 300) {
                throw new RuntimeException("Failed to send WhatsApp: HTTP "
                        + res.getStatus() + " - " + res.getBody());
            }
        } catch (UnirestException e) {
            throw new RuntimeException("Failed to send WhatsApp", e);
        }
    }



    public void sendImageUrl(String to, String imageUrl, String caption) {
        String normalizedTo = normalizeTo(to);
        String url = api("messages/image");
        try {
            HttpResponse<String> res = Unirest.post(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", token)
                    .field("to", normalizedTo)
                    .field("image", imageUrl)
                    .field("caption", caption == null ? "" : caption)
                    .asString();

            if (res.getStatus() < 200 || res.getStatus() >= 300) {
                throw new RuntimeException("Failed to send WhatsApp image: HTTP "
                        + res.getStatus() + " - " + res.getBody());
            }
        } catch (UnirestException e) {
            throw new RuntimeException("Failed to send WhatsApp image", e);
        }
    }


    public void sendDocumentUrl(String to, String documentUrl, String filename, String caption) {
        String normalizedTo = normalizeTo(to);
        String url = api("messages/document");
        try {
            HttpResponse<String> res = Unirest.post(url)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .field("token", token)
                    .field("to", normalizedTo)
                    .field("document", documentUrl)
                    .field("filename", filename)
                    .field("caption", caption == null ? "" : caption)
                    .asString();

            if (res.getStatus() < 200 || res.getStatus() >= 300) {
                throw new RuntimeException("Failed to send WhatsApp document: HTTP "
                        + res.getStatus() + " - " + res.getBody());
            }
        } catch (UnirestException e) {
            throw new RuntimeException("Failed to send WhatsApp document", e);
        }
    }

    // ===== Helpers =====
    private String api(String path) {
        if (instanceId == null || instanceId.isBlank())
            throw new ApiException("missing ULTRAMSG_INSTANCE_ID");
        if (token == null || token.isBlank())
            throw new IllegalStateException("missing ULTRAMSG_TOKEN");
        return String.format("%s/%s/%s", baseUrl, instanceId, path);
    }

    private String normalizeTo(String to) {
        if (to == null) return null;
        String n = to.trim();
        if (n.startsWith("+")) n = n.substring(1);
        return n.replaceAll("\\s+", "");
    }
}
