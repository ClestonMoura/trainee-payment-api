package br.com.flexpag.traineepaymentapi.dto;

import br.com.flexpag.traineepaymentapi.entity.enums.Role;

public record RegisterFormDTO(
        String username,
        String email,
        String password,
        Role role
) {
}
