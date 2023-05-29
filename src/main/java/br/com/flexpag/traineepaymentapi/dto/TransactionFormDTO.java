package br.com.flexpag.traineepaymentapi.dto;

import br.com.flexpag.traineepaymentapi.entity.enums.PaymentTypeEnum;

public record TransactionFormDTO(
        PaymentTypeEnum paymentType) {
}
