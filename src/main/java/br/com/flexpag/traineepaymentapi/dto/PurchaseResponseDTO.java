package br.com.flexpag.traineepaymentapi.dto;

import java.time.LocalDate;
import java.util.List;

public record PurchaseResponseDTO(
        Long id,
        LocalDate createdOn,
        LocalDate updatedOn,
        Long amount,
        Long invoiceAmount,
        Long clientId,
        List<InvoiceResponseDTO> invoices) {
}
