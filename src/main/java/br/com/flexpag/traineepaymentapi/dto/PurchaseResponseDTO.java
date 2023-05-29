package br.com.flexpag.traineepaymentapi.dto;

import java.time.LocalDate;

public record PurchaseResponseDTO(
        Long id,
        LocalDate createdOn,
        LocalDate updatedOn,
        Long amount,
        Long invoiceAmount,
        Double fee,
        Long clientId) {
}
