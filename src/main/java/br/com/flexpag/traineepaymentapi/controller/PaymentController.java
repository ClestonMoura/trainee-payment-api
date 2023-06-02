package br.com.flexpag.traineepaymentapi.controller;

import br.com.flexpag.traineepaymentapi.dto.*;
import br.com.flexpag.traineepaymentapi.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

/**
 * Classe de controle responsável pelas principais funcionalidades da API
 */
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearer-key")
public class PaymentController {

    private final PaymentService paymentService;

    /**
     * Requisição para adicionar invoices a aplicação
     * @param invoiceFormDTOS Formulario para adição de invoices
     * @return Um DTO com as invoices adicionadas
     */
    @PostMapping("/clients/invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> addInvoices(@RequestBody List<InvoiceFormDTO> invoiceFormDTOS) {
        var newInvoices = paymentService.addInvoices(invoiceFormDTOS);
        return ResponseEntity.ok(newInvoices);
    }

    /**
     * Requisição para a consulta das invoices
     * @param clientId Id do cliente para a consulta das invoices
     * @return Um DTO com a as invoices do cliente
     */
    @GetMapping("/clients/{clientId}/invoices")
    public ResponseEntity<List<InvoiceResponseDTO>> getInvoices(@PathVariable Long clientId) {
        var invoices = paymentService.getInvoices(clientId);
        return ResponseEntity.ok(invoices);
    }

    /**
     * Requisição para criação de uma nova purchase
     * @param clientId Id do cliente
     * @param purchaseFormDTO Formulário para a criaçãod da purchase
     * @param builder Criação do URI
     * @return Um DTO com a purchase criada
     */
    @PostMapping("/clients/{clientId}/purchases")
    public ResponseEntity<PurchaseResponseDTO> createPurchase(@PathVariable Long clientId,
                                                              @RequestBody PurchaseFormDTO purchaseFormDTO,
                                                              UriComponentsBuilder builder) {
        var newPurchase = paymentService.createPurchase(clientId, purchaseFormDTO);
        var uri = builder.path("/clients/{clientId}/purchases/{id}")
                .buildAndExpand(clientId, newPurchase.id()).toUri();
        return ResponseEntity.created(uri).body(newPurchase);
    }

    /**
     * Requisição para criar uma nva transaction
     * @param purchaseId Id da purchase
     * @param transactionFormDTO Formulário de criação da transaction
     * @param builder Criação do URI
     * @return Um DTO com a transaction criada
     */
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
