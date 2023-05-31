package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.InvoiceFormDTO;
import br.com.flexpag.traineepaymentapi.dto.InvoiceResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseFormDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseResponseDTO;
import br.com.flexpag.traineepaymentapi.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/clients/{clientId}/invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> addInvoices(@PathVariable Long clientId,
                                                                @RequestBody List<InvoiceFormDTO> invoiceFormDTOS) {
        var newInvoices = paymentService.addInvoices(clientId, invoiceFormDTOS);
        return ResponseEntity.ok(newInvoices);
    }

    @GetMapping("/clients/{clientId}/invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoices(@PathVariable Long clientId) {
        var invoices = paymentService.getInvoices(clientId);
        return ResponseEntity.ok(invoices);
    }

    @PostMapping("/clients/{clientId}/purchases")
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@PathVariable Long clientId,
                                                   @RequestBody PurchaseFormDTO purchaseFormDTO,
                                                   UriComponentsBuilder builder) {
        var newPurchase = paymentService.createPurchase(clientId, purchaseFormDTO);
        var uri = builder.path("/api/payments/clients/{clientId}/purchases/{id}")
                .buildAndExpand(clientId, newPurchase.id()).toUri();
        return ResponseEntity.created(uri).body(newPurchase);
    }

}
