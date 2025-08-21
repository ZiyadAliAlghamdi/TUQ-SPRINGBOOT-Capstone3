package org.example.capstone3.Repository;

import org.example.capstone3.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    Invoice findInvoiceById(Integer id);

    Invoice findInvoiceByPaymentId(String paymentId);

    @Query("select i from Invoice i where i.booking.billboard.lessor.id = ?1")
    List<Invoice> findInvoiceByLessor(Integer lessorId);
}
