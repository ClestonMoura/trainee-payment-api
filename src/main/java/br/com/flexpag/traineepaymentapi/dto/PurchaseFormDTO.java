package br.com.flexpag.traineepaymentapi.dto;

public record PurchaseFormDTO(
        Long amount,
        Long invoiceAmount,
        Double fee,
        Long clientId) {
}
