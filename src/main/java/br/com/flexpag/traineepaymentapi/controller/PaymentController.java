package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.*;
import br.com.flexpag.traineepaymentapi.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/clients/invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> addInvoices(@RequestBody List<InvoiceFormDTO> invoiceFormDTOS) {
        var newInvoices = paymentService.addInvoices(invoiceFormDTOS);
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
        var uri = builder.path("/clients/{clientId}/purchases/{id}")
                .buildAndExpand(clientId, newPurchase.id()).toUri();
        return ResponseEntity.created(uri).body(newPurchase);
    }

    @PostMapping("/purchases/{purchaseId}/transactions")
    public ResponseEntity<TransactionResponseDTO> createTransaction(@PathVariable Long purchaseId,
                                                                    @RequestBody
                                                                    TransactionFormDTO transactionFormDTO,
                                                                    UriComponentsBuilder builder) {
        var newTransaction = paymentService.createTransaction(purchaseId, transactionFormDTO);
        var uri = builder.path("/purchases/{purchaseId}/transactions/{id}")
                .buildAndExpand(purchaseId, newTransaction.id()).toUri();
        return ResponseEntity.created(uri).body(newTransaction);
    }

}
