package br.com.flexpag.traineepaymentapi.dto;

import java.time.LocalDate;

public record InvoiceFormDTO(
        LocalDate dueDate,
        String barcode,
        Double amount,
        Long contractNumber) {
}
