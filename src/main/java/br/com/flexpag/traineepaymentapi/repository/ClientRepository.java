package br.com.flexpag.traineepaymentapi.repository;

import br.com.flexpag.traineepaymentapi.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
