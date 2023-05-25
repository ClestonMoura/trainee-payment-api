package br.com.flexpag.traineepaymentapi.dto;

public record AddressResponseDTO(
        String publicPlace,
        String complement,
        String neighborhood,
        String city,
        String state) {
}
