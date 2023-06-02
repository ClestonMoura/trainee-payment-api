package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.AddressDTO;
import br.com.flexpag.traineepaymentapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe de controle para requisição do endreço
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    /**
     * Requisição para recuperar os dados de endereço do ViaCEP
     * @param cep Código de Endereçamento Postal
     * @return Um DTO de endereço
     */
    @GetMapping("/address")
    public ResponseEntity<AddressDTO> getAddressByCep(@RequestParam(name = "cep") String cep) {
        var addressResponse = service.getAddressByCep(cep);
        return ResponseEntity.ok(addressResponse);
    }

}
