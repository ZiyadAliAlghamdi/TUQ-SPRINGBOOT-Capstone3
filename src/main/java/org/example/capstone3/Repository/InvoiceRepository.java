package org.example.capstone3.Repository;

import org.example.capstone3.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findInvoiceById(Integer id);

    Invoice findInvoiceByPaymentId(String paymentId);
}
