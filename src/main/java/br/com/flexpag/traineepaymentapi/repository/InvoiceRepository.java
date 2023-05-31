package br.com.flexpag.traineepaymentapi.repository;

import br.com.flexpag.traineepaymentapi.entity.Client;
import br.com.flexpag.traineepaymentapi.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

    List<Invoice> findAllByContractNumber(Long contractNumber);

    List<Invoice> findAllByClientAndPaid(Client client, boolean paid);

}
