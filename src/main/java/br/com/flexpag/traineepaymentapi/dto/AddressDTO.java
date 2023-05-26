package br.com.flexpag.traineepaymentapi.dto;

public record AddressDTO(
        String publicPlace,
        String complement,
        String neighborhood,
        String city,
        String state) {
}
