package br.com.flexpag.traineepaymentapi.dto;

import br.com.flexpag.traineepaymentapi.entity.enums.PaymentTypeEnum;
import br.com.flexpag.traineepaymentapi.entity.enums.StatusEnum;

import java.time.LocalDate;

public record TransactionResponseDTO(
        Long id,
        LocalDate createdOn,
        LocalDate updatedOn,
        PaymentTypeEnum paymentType,
        StatusEnum status,
        Long authorizationCode,
        Long purchaseId) {
}
