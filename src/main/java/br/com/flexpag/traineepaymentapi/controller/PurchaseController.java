package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.InvoiceFormDTO;
import br.com.flexpag.traineepaymentapi.dto.InvoiceResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseFormDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseResponseDTO;
import br.com.flexpag.traineepaymentapi.service.InvoiceService;
import br.com.flexpag.traineepaymentapi.service.PurchaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/purchase")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final InvoiceService invoiceService;

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> addPurchase(@RequestBody PurchaseFormDTO request,
                                                           UriComponentsBuilder builder) {
        var newPurchase = purchaseService.addPurchase(request);
        var uri = builder.path("/api/purchase/{id}").buildAndExpand(newPurchase.id()).toUri();
        return ResponseEntity.created(uri).body(newPurchase);
    }

    @PostMapping("/{purchaseId}/invoice")
    public ResponseEntity<InvoiceResponseDTO> addInvoice(@PathVariable Long purchaseId,
                                                         @RequestBody InvoiceFormDTO request,
                                                         UriComponentsBuilder builder) {
        var newInvoice = invoiceService.addInvoice(request, purchaseId);
        var uri = builder.path("/api/purchase/{purchaseId}/invoice/{id}")
                .buildAndExpand(purchaseId, newInvoice.id()).toUri();
        return ResponseEntity.created(uri).body(newInvoice);
    }

    @GetMapping("/{purchaseId}/invoice/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(@PathVariable Long purchaseId,
                                                         @PathVariable Long id) {
        var invoice = invoiceService.getInvoice(purchaseId, id);
        return ResponseEntity.ok(invoice);
    }

}
