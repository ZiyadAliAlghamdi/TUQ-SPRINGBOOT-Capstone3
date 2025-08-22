package org.example.capstone3.Service;

import com.adobe.pdfservices.operation.PDFServices;
import com.adobe.pdfservices.operation.PDFServicesMediaType;
import com.adobe.pdfservices.operation.PDFServicesResponse;
import com.adobe.pdfservices.operation.io.Asset;
import com.adobe.pdfservices.operation.io.StreamAsset;
import com.adobe.pdfservices.operation.pdfjobs.jobs.DocumentMergeJob;
import com.adobe.pdfservices.operation.pdfjobs.params.documentmerge.DocumentMergeParams;
import com.adobe.pdfservices.operation.pdfjobs.params.documentmerge.OutputFormat;
import com.adobe.pdfservices.operation.pdfjobs.result.DocumentMergeResult;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.example.capstone3.Model.Booking;
import org.example.capstone3.Model.Invoice;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InvoicePdfService {

    private final PDFServices pdfServices;


    @Value("${adobe.pdfservices.templatePath}")
    private Resource templateDocx;


    public byte[] generateInvoicePdf(Booking booking, Invoice invoice) {
        try (InputStream templateStream = templateDocx.getInputStream()) {

            // 1) Upload the DOCX template as an Asset
            Asset templateAsset = pdfServices.upload(
                    templateStream,
                    PDFServicesMediaType.DOCX.getMediaType()
            );

            // 2) Build the JSON payload: { "booking": { ... }, "invoice": { ... } }
            ObjectMapper mapper = new ObjectMapper()
                    .registerModule(new JavaTimeModule())
                    .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            Map<String, Object> bookingMap =
                    mapper.convertValue(booking, new TypeReference<>() {});
            Map<String, Object> invoiceMap =
                    mapper.convertValue(invoice, new TypeReference<>() {});

            JSONObject data = new JSONObject()
                    .put("booking", new JSONObject(bookingMap))
                    .put("invoice", new JSONObject(invoiceMap));

            // 3) Build job params: output PDF (could be DOCX if you prefer)
            DocumentMergeParams params = DocumentMergeParams
                    .documentMergeParamsBuilder()
                    .withJsonDataForMerge(data)
                    .withOutputFormat(OutputFormat.PDF)
                    .build();

            // 4) Submit merge job and fetch result
            DocumentMergeJob job = new DocumentMergeJob(templateAsset, params);
            String location = pdfServices.submit(job);

            PDFServicesResponse<DocumentMergeResult> response =
                    pdfServices.getJobResult(location, DocumentMergeResult.class);

            StreamAsset result = pdfServices.getContent(response.getResult().getAsset());
            return result.getInputStream().readAllBytes();

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate invoice PDF", e);
        }
    }
}
