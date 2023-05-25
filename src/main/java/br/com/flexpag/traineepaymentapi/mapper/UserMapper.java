package br.com.flexpag.traineepaymentapi.mapper;

import br.com.flexpag.traineepaymentapi.dto.RegisterRequestDTO;
import br.com.flexpag.traineepaymentapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapToUser(RegisterRequestDTO registerRequestDTO) {
        return User.builder()
                .username(registerRequestDTO.username())
                .email(registerRequestDTO.email())
                .password(passwordEncoder.encode(registerRequestDTO.password()))
                .role(registerRequestDTO.role())
                .build();
    }

}
