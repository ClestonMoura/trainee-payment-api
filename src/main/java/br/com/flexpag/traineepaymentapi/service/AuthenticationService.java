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

/**
 * Classe de ser viço de autenticação do usuário
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final EntityMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Registra um novo usuário na aplicação
     * @param registerFormDTO Formulario de registro
     * @return Um DTO do usuário criado e o token JWT
     */
    public AuthResponseDTO register(RegisterFormDTO registerFormDTO) {
        var user = mapper.mapToUser(registerFormDTO);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return mapper.maptoAuthResponseDTO(savedUser, jwtToken);
    }

    /**
     * Authentica um usuário já cadastrado na aplicação
     * @param authFormDTO Um formulário de login
     * @return Um DTO do usuário logado com o token JWT
     */
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
