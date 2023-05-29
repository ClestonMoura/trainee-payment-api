package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.*;
import br.com.flexpag.traineepaymentapi.service.InvoiceService;
import br.com.flexpag.traineepaymentapi.service.PurchaseService;
import br.com.flexpag.traineepaymentapi.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final InvoiceService invoiceService;
    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<PurchaseResponseDTO> addPurchase(@RequestBody PurchaseFormDTO request,
                                                           UriComponentsBuilder builder) {
        var newPurchase = purchaseService.addPurchase(request);
        var uri = builder.path("/api/purchases/{id}").buildAndExpand(newPurchase.id()).toUri();
        return ResponseEntity.created(uri).body(newPurchase);
    }

    @PostMapping("/{purchaseId}/invoices")
    public ResponseEntity<InvoiceResponseDTO> addInvoice(@PathVariable Long purchaseId,
                                                         @RequestBody InvoiceFormDTO request,
                                                         UriComponentsBuilder builder) {
        var newInvoice = invoiceService.addInvoice(request, purchaseId);
        var uri = builder.path("/api/purchases/{purchaseId}/invoice/{id}")
                .buildAndExpand(purchaseId, newInvoice.id()).toUri();
        return ResponseEntity.created(uri).body(newInvoice);
    }

    @GetMapping("/{purchaseId}/invoices/{id}")
    public ResponseEntity<InvoiceResponseDTO> getInvoice(@PathVariable Long purchaseId,
                                                         @PathVariable Long id) {
        var invoice = invoiceService.getInvoice(purchaseId, id);
        return ResponseEntity.ok(invoice);
    }

    @PostMapping("/{purchaseId}/transactions")
    public ResponseEntity<TransactionResponseDTO> addTransaction(@PathVariable Long purchaseId,
                                                 @RequestBody TransactionFormDTO request,
                                                 UriComponentsBuilder builder) {
        var newTransaction = transactionService.addTransaction(request, purchaseId);
        var uri = builder.path("/api/purchases/{purchaseId}/invoice/{id}")
                .buildAndExpand(purchaseId, newTransaction.id()).toUri();
        return ResponseEntity.created(uri).body(newTransaction);
    }

    @PutMapping("/{purchaseId}/transactions/{id}")
    public ResponseEntity<TransactionResponseDTO> updateTransactionStatus(@PathVariable Long purchaseId,
                                                                          @PathVariable Long id) {
        var transaction = transactionService.updateTransactionStatus(purchaseId, id);
        return ResponseEntity.ok(transaction);
    }

}
