package org.example.capstone3.Service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.example.capstone3.Model.Invoice;
import org.example.capstone3.Model.Advertiser;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
public class InvoicePdfBuilder {

    public byte[] build(Invoice invoice,
                        Advertiser advertiser,
                        String bookingRef,
                        String paymentId,
                        String cardBrand,
                        String maskedCardOrLast4) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document doc = new Document(PageSize.A4, 36, 36, 36, 36);

        try {
            PdfWriter.getInstance(doc, baos);
            doc.open();


            Paragraph title = new Paragraph("Payment Receipt", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            doc.add(title);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Company: " + advertiser.getCompanyName()));
            doc.add(new Paragraph("Brand: " + advertiser.getBrandName()));
            doc.add(new Paragraph("Email: " + advertiser.getEmail()));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Booking Ref: " + bookingRef));
            doc.add(new Paragraph("Payment ID: " + (paymentId == null ? "-" : paymentId)));
            doc.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);

            addRow(table, "Amount", String.format("%.2f %s", invoice.getAmount(), invoice.getCurrency()));
            addRow(table, "Status", invoice.getStatus());
            addRow(table, "Due Date", invoice.getDueDate() == null ? "-" :
                    invoice.getDueDate().format(DateTimeFormatter.ISO_DATE));
            addRow(table, "Card", (cardBrand == null ? "" : (cardBrand + " ")) +
                    (maskedCardOrLast4 == null ? "" : maskedCardOrLast4));

            doc.add(table);

            doc.add(new Paragraph(" "));
            Paragraph note = new Paragraph(
                    "Note: For your security, full card data is never emailed or stored in the PDF.",
                    FontFactory.getFont(FontFactory.HELVETICA, 9, Color.GRAY)
            );
            doc.add(note);

        } catch (Exception e) {
            throw new RuntimeException("Failed to build invoice PDF", e);
        } finally {
            doc.close();
        }
        return baos.toByteArray();
    }

    private void addRow(PdfPTable table, String k, String v) {
        PdfPCell c1 = new PdfPCell(new Phrase(k, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
        c1.setBorderWidth(0.5f);
        PdfPCell c2 = new PdfPCell(new Phrase(v == null ? "-" : v, FontFactory.getFont(FontFactory.HELVETICA, 11)));
        c2.setBorderWidth(0.5f);
        table.addCell(c1);
        table.addCell(c2);
    }
}
