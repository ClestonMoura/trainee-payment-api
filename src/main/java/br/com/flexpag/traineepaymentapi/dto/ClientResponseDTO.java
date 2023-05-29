package br.com.flexpag.traineepaymentapi.dto;

import br.com.flexpag.traineepaymentapi.entity.enums.ContractTypeEnum;

import java.time.LocalDate;

public record ClientResponseDTO(
        Long id,
        LocalDate createdOn,
        LocalDate updatedOn,
        String firstName,
        String lastName,
        String identity,
        ContractTypeEnum contractType,
        AddressDTO address
) {
}
