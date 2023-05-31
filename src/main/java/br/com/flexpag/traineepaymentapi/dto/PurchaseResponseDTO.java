package br.com.flexpag.traineepaymentapi.dto;

import java.time.LocalDate;
import java.util.List;

public record PurchaseResponseDTO(
        Long id,
        LocalDate createdOn,
        LocalDate updatedOn,
        Double amount,
        Double invoiceAmount,
        Double fee,
        Long clientId,
        List<InvoiceResponseDTO> invoices) {
}
