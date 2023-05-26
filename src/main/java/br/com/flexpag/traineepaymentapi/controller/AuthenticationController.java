package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.AuthRequestDTO;
import br.com.flexpag.traineepaymentapi.dto.AuthResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.RegisterRequestDTO;
import br.com.flexpag.traineepaymentapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@RequestBody RegisterRequestDTO registerRequestDTO) {
        return ResponseEntity.ok(service.register(registerRequestDTO));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody AuthRequestDTO authRequestDTO) {
        return ResponseEntity.ok(service.authenticate(authRequestDTO));
    }

}