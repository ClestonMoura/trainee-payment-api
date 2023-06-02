package br.com.flexpag.traineepaymentapi.dto;

import java.time.LocalDate;

public record InvoiceResponseDTO(
        Long id,
        LocalDate createdOn,
        LocalDate updatedOn,
        LocalDate dueDate,
        String barcode,
        Double amount,
        Boolean paid,
        Long contractNumber) {
}
