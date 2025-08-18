package org.example.capstone3.Service;

import lombok.RequiredArgsConstructor;
import org.example.capstone3.Api.ApiException;
import org.example.capstone3.Model.Invoice;
import org.example.capstone3.Repository.InvoiceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public List<Invoice> getAllInvoice(){
        return invoiceRepository.findAll();
    }

    public void addInvoice(Invoice invoice){
        invoiceRepository.save(invoice);
    }

    public void updateInvoice(Integer id , Invoice invoice){
        Invoice invoice1 = invoiceRepository.findInvoiceById(id);
        if (invoice1 == null){
            throw new ApiException("Invoice with id " + id + " not found");
        }
        invoice1.setAmountDue(invoice.getAmountDue());
        invoice1.setCurrency(invoice.getCurrency());
        invoice1.setDueDate(invoice.getDueDate());
        invoice1.setStatus(invoice.getStatus());
        invoiceRepository.save(invoice1);
    }

    public void deleteInvoice(Integer id){
        Invoice invoice = invoiceRepository.findInvoiceById(id);
        if (invoice == null){
            throw new ApiException("Invoice with id " + id + " not found");
        }
        invoiceRepository.delete(invoice);
    }
}
