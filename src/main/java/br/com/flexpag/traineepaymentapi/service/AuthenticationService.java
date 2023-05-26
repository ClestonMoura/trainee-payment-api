package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.config.JwtService;
import br.com.flexpag.traineepaymentapi.dto.AuthRequestDTO;
import br.com.flexpag.traineepaymentapi.dto.AuthResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.RegisterRequestDTO;
import br.com.flexpag.traineepaymentapi.mapper.UserMapper;
import br.com.flexpag.traineepaymentapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        var user = mapper.mapToUser(registerRequestDTO);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return mapper.maptoAuthResponseDTO(savedUser, jwtToken);
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequestDTO.username(),
                        authRequestDTO.password()
                )
        );
        var user = repository.findByUsername(authRequestDTO.username()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return mapper.maptoAuthResponseDTO(user, jwtToken);
    }

}
