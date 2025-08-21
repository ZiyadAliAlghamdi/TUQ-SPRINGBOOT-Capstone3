package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.CampaignAsset;
import org.example.capstone3.Service.CampaignAssetService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/campaign-asset")
@RequiredArgsConstructor
public class CampaignAssetController {

    private final CampaignAssetService campaignAssetService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllCampaignAsset(){
        return ResponseEntity.status(200).body(campaignAssetService.getAllCampaignAsset());
    }

    @PostMapping(
            value = "/add/{campaign_id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> addCampaignAsset(@PathVariable Integer campaign_id, @RequestPart("file") MultipartFile file
    ) throws IOException {
        campaignAssetService.addCampaignAsset(campaign_id, file);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign Asset added successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCampaignAsset(@PathVariable Integer id){
        campaignAssetService.deleteCampaignAsset(id);
        return ResponseEntity.status(200).body(new ApiResponse("Campaign Asset deleted successfully"));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getAssetFile(@PathVariable Integer id) {
        CampaignAsset asset = campaignAssetService.getCampaignAssetById(id); // add this in the service (next section)

        MediaType mediaType = resolveMediaType(asset.getFileName());
        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + asset.getFileName().replace("\"","") + "\"")
                .body(asset.getFileContent());
    }

    private MediaType resolveMediaType(String fileName) {
        String ext = "";
        int dot = fileName.lastIndexOf('.');
        if (dot > -1 && dot < fileName.length() - 1) {
            ext = fileName.substring(dot + 1).toLowerCase();
        }
        switch (ext) {
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            case "webp":
                return MediaType.parseMediaType("image/webp");
            case "bmp":
                return MediaType.parseMediaType("image/bmp");
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}
