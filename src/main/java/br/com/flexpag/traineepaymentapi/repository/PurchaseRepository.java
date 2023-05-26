package br.com.flexpag.traineepaymentapi.repository;

import br.com.flexpag.traineepaymentapi.entity.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}
