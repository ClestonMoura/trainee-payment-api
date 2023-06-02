package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.*;
import br.com.flexpag.traineepaymentapi.entity.Invoice;
import br.com.flexpag.traineepaymentapi.entity.Purchase;
import br.com.flexpag.traineepaymentapi.entity.Transaction;
import br.com.flexpag.traineepaymentapi.entity.enums.StatusEnum;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import br.com.flexpag.traineepaymentapi.repository.ClientRepository;
import br.com.flexpag.traineepaymentapi.repository.InvoiceRepository;
import br.com.flexpag.traineepaymentapi.repository.PurchaseRepository;
import br.com.flexpag.traineepaymentapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

/**
 * Classe de serviço das principais funcionalidades da aplicação
 */
@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final InvoiceRepository invoiceRepository;
    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;
    private final EntityMapper mapper;

    /**
     * Adiciona novas invoices
     * @param invoiceFormDTOS Formulário de invoices
     * @return Um DTO das invoices criadas
     */
    public List<InvoiceResponseDTO> addInvoices(List<InvoiceFormDTO> invoiceFormDTOS) {
        var newInvoices = invoiceFormDTOS.stream().map(mapper::mapToInvoice).toList();
        newInvoices.forEach(invoice -> invoice.setPaid(false));
        return invoiceRepository.saveAll(newInvoices).stream().map(mapper::mapToInvoiceResponseDTO).toList();
    }

    /**
     * Consulta de invoices
     * @param clientId Id do cliente
     * @return Um DTO com as invoices do cliente
     */
    public List<InvoiceResponseDTO> getInvoices(Long clientId) {
        var client = clientRepository.findById(clientId).orElseThrow();
        var invoices = invoiceRepository.findAllByContractNumberAndPaid(client.getContractNumber(), false);
        return invoices.stream().map(mapper::mapToInvoiceResponseDTO).toList();
    }

    /**
     * Criação de uma nova purchase
     * @param clientId Id do Cliente
     * @param purchaseFormDTO Formulário de criação da purchase
     * @return Um DTO da purchase criada
     */
    public PurchaseResponseDTO createPurchase(Long clientId, PurchaseFormDTO purchaseFormDTO) {
        var newPurchase = new Purchase();
        List<Invoice> invoices = invoiceRepository.findAllById(purchaseFormDTO.invoicesId());
        var client = clientRepository.findById(clientId).orElseThrow();
        newPurchase.setClient(client);
        newPurchase.setAmount(purchaseFormDTO.amount());
        newPurchase.setInvoiceAmount(purchaseFormDTO.invoiceAmount());
        newPurchase.setInvoices(invoices);
        invoices.forEach(invoice -> invoice.setPurchase(newPurchase));
        return mapper.mapToPurchaseResponseDTO(clientId, purchaseRepository.save(newPurchase));
    }

    /**
     * Criação de uma nova transaction
     * @param purchaseId Id da purchase
     * @param transactionFormDTO Formulário de criação da transaction
     * @return Um DTO da purchase criada
     */
    public TransactionResponseDTO createTransaction(Long purchaseId, TransactionFormDTO transactionFormDTO) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow();
        var newTransaction = new Transaction();
        newTransaction.setPaymentType(transactionFormDTO.paymentType());
        newTransaction.setInstallments(transactionFormDTO.installments());
        newTransaction.setStatus(randomStatusGenerator());
        newTransaction.setAuthorizationCode(randomIntegerWithSize(10));
        newTransaction.setPurchase(purchase);
        return mapper.mapToTransactionResponseDTO(
                transactionRepository.save(newTransaction), purchaseId);
    }

    /**
     * Método axiliar para gerar um status
     * @return StatusEnum
     */
    private StatusEnum randomStatusGenerator() {
        var random = new Random();
        StatusEnum[] values = StatusEnum.values();
        int index = random.nextInt(values.length);
        return values[index];
    }

    /**
     * Método auxiliar para gerar um código de autorização
     * @param size Tamanho do número gerado
     * @return Um int aleatório
     */
    private long randomIntegerWithSize(int size) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < size; i++) {
            int digit = random.nextInt(10);
            builder.append(digit);
        }

        return Long.parseLong(builder.toString());
    }

}
