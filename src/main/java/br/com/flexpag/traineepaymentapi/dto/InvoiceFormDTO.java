package br.com.flexpag.traineepaymentapi.dto;

import java.time.LocalDate;

public record InvoiceFormDTO(
        LocalDate dueDate,
        String barcode,
        Long amount,
        Long contractNumber,
        Long purchaseId) {
}
