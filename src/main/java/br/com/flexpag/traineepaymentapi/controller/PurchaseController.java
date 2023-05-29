package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.PurchaseFormDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseResponseDTO;
import br.com.flexpag.traineepaymentapi.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService service;

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> addPurchase(@RequestBody PurchaseFormDTO request,
                                                           UriComponentsBuilder builder) {
        var newPurchase = service.addPurchase(request);
        var uri = builder.path("/api/purchase/{id}").buildAndExpand(newPurchase.id()).toUri();
        return ResponseEntity.created(uri).body(newPurchase);
    }

}
