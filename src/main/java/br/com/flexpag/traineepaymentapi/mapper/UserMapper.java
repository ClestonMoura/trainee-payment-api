package br.com.flexpag.traineepaymentapi.mapper;

import br.com.flexpag.traineepaymentapi.dto.AuthResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.RegisterFormDTO;
import br.com.flexpag.traineepaymentapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User mapToUser(RegisterFormDTO registerFormDTO) {
        return User.builder()
                .username(registerFormDTO.username())
                .email(registerFormDTO.email())
                .password(passwordEncoder.encode(registerFormDTO.password()))
                .role(registerFormDTO.role())
                .build();
    }

    public AuthResponseDTO maptoAuthResponseDTO(User user, String jwtToken) {
        return new AuthResponseDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                jwtToken
        );
    }

}
