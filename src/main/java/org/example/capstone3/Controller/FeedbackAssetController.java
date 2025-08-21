package org.example.capstone3.Controller;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiResponse;
import org.example.capstone3.Model.FeedbackAsset;
import org.example.capstone3.Service.FeedbackAssetService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/feedback-asset")
@RequiredArgsConstructor
public class FeedbackAssetController {

    private final FeedbackAssetService feedbackAssetService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllFeedbackAsset(){
        return ResponseEntity.status(200).body(feedbackAssetService.getAllFeedbackAsset());
    }

    @PostMapping(
            value = "/add/{feedback_id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )    public ResponseEntity<?> addFeedbackAsset(@PathVariable Integer feedback_id, @RequestPart("file") MultipartFile file
    ) throws IOException {
        feedbackAssetService.addFeedbackAsset(feedback_id, file);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback Asset added successfully"));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteFeedbackAsset(@PathVariable Integer id){
        feedbackAssetService.deleteFeedbackAsset(id);
        return ResponseEntity.status(200).body(new ApiResponse("Feedback Asset deleted successfully"));
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<byte[]> getAssetFile(@PathVariable Integer id) {
        FeedbackAsset asset = feedbackAssetService.getFeedBackAssetByID(id); // add this in the service (next section)

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
