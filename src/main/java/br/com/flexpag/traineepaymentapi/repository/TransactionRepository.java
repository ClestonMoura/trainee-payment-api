package br.com.flexpag.traineepaymentapi.repository;

import br.com.flexpag.traineepaymentapi.entity.Purchase;
import br.com.flexpag.traineepaymentapi.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Optional<Transaction> findByPurchaseAndId(Purchase purchase, Long id);

}
