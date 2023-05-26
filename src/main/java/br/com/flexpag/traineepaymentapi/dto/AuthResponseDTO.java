package br.com.flexpag.traineepaymentapi.dto;

import br.com.flexpag.traineepaymentapi.entity.enums.Role;

public record AuthResponseDTO(
        String username,
        String email,
        Role role,
        String accessToken) {
}
