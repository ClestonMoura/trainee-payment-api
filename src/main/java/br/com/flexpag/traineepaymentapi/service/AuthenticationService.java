package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.config.JwtService;
import br.com.flexpag.traineepaymentapi.dto.AuthFormDTO;
import br.com.flexpag.traineepaymentapi.dto.AuthResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.RegisterFormDTO;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import br.com.flexpag.traineepaymentapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final EntityMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponseDTO register(RegisterFormDTO registerFormDTO) {
        var user = mapper.mapToUser(registerFormDTO);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return mapper.maptoAuthResponseDTO(savedUser, jwtToken);
    }

    public AuthResponseDTO authenticate(AuthFormDTO authFormDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authFormDTO.username(),
                        authFormDTO.password()
                )
        );
        var user = repository.findByUsername(authFormDTO.username()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return mapper.maptoAuthResponseDTO(user, jwtToken);
    }

}
