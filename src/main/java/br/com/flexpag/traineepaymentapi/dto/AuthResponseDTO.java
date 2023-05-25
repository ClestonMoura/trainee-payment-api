package br.com.flexpag.traineepaymentapi.dto;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken) {
}
