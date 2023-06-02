package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.AuthFormDTO;
import br.com.flexpag.traineepaymentapi.dto.AuthResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.RegisterFormDTO;
import br.com.flexpag.traineepaymentapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe de controle responsavel pela autenticação do usuário
 */
@RestController
@RequestMapping("/api/payments/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    /**
     * Registro de um novo usuário no sistema
     * @param registerFormDTO Formulário de registro
     * @return Um DTO do usuário criado com o token JWT
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterFormDTO registerFormDTO) {
        return ResponseEntity.ok(service.register(registerFormDTO));
    }

    /**
     * Autenticação de um usuário já cadastrado
     * @param authFormDTO Formulário de login
     * @return Um DTO do usuário logado com o token JWT
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthFormDTO authFormDTO) {
        return ResponseEntity.ok(service.authenticate(authFormDTO));
    }

}
