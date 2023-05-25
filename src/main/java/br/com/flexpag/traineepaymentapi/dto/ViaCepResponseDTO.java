package br.com.flexpag.traineepaymentapi.dto;

public record ViaCepResponseDTO(
        String logradouro,
        String complemento,
        String bairro,
        String localidade,
        String uf
) {
}
