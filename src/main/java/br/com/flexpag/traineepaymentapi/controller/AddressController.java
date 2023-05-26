package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.AddressDTO;
import br.com.flexpag.traineepaymentapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService service;

    @GetMapping
    public ResponseEntity<AddressDTO> getAddressByCep(@RequestParam(name = "cep") String cep) {
        var addressResponse = service.getAddressByCep(cep);
        return ResponseEntity.ok(addressResponse);
    }

}
