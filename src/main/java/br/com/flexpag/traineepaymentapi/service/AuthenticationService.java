package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.config.JwtService;
import br.com.flexpag.traineepaymentapi.dto.AuthRequestDTO;
import br.com.flexpag.traineepaymentapi.dto.AuthResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.RegisterRequestDTO;
import br.com.flexpag.traineepaymentapi.entity.Token;
import br.com.flexpag.traineepaymentapi.entity.User;
import br.com.flexpag.traineepaymentapi.entity.enums.TokenTypeEnum;
import br.com.flexpag.traineepaymentapi.mapper.UserMapper;
import br.com.flexpag.traineepaymentapi.repository.TokenRepository;
import br.com.flexpag.traineepaymentapi.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final UserMapper mapper;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public AuthResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        var user = mapper.mapToUser(registerRequestDTO);
        var savedUser = repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new AuthResponseDTO(jwtToken, refreshToken);
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
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAlluserTokens(user);
        saveUserToken(user, jwtToken);
        return new AuthResponseDTO(jwtToken, refreshToken);
    }


    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }

        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);

        if (username != null) {
            var user = this.repository.findByUsername(refreshToken).orElseThrow();

            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAlluserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = new AuthResponseDTO(accessToken, refreshToken);
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .tokenName(jwtToken)
                .tokenType(TokenTypeEnum.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAlluserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());

        if (validUserTokens.isEmpty()) {
            return;
        }

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

}
