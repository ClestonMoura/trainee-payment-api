package br.com.flexpag.traineepaymentapi.repository;

import br.com.flexpag.traineepaymentapi.entity.Invoice;
import br.com.flexpag.traineepaymentapi.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByPurchase(Purchase purchase);

    Optional<Invoice> findByPurchaseAndId(Purchase purchase, Long id);

}
