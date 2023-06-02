package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.ClientFormDTO;
import br.com.flexpag.traineepaymentapi.dto.ClientResponseDTO;
import br.com.flexpag.traineepaymentapi.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class ClientController {

    private final ClientService service;

    @PostMapping("/clients")
    public ResponseEntity<ClientResponseDTO> registerClient(@RequestBody ClientFormDTO request,
                                                            UriComponentsBuilder builder) {
        var newClient = service.registerClient(request);
        var uri = builder.path("/api/clients/{id}").buildAndExpand(newClient.id()).toUri();
        return ResponseEntity.created(uri).body(newClient);
    }

}
