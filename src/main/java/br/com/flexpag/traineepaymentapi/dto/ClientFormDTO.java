package br.com.flexpag.traineepaymentapi.dto;

import br.com.flexpag.traineepaymentapi.entity.enums.ContractTypeEnum;

public record ClientFormDTO(
        String firstName,
        String lastName,
        String identity,
        ContractTypeEnum contractType,
        Long contractNumber,
        AddressDTO address) {

}
