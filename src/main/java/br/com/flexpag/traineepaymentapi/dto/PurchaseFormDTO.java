package br.com.flexpag.traineepaymentapi.dto;

import java.util.List;

public record PurchaseFormDTO(
        Long amount,
        Long invoiceAmount,
        List<Long> invoicesId) {
}
