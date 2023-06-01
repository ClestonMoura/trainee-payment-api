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

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final InvoiceRepository invoiceRepository;
    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
    private final TransactionRepository transactionRepository;
    private final EntityMapper mapper;

    public List<InvoiceResponseDTO> addInvoices(Long clientId, List<InvoiceFormDTO> invoiceFormDTOS) {
        var client = clientRepository.findById(clientId).orElseThrow();
        var newInvoices = invoiceFormDTOS.stream().map(mapper::mapToInvoice).toList();
        newInvoices.forEach(invoice -> {
                invoice.setClient(client);
                invoice.setPaid(false);
        });
        return invoiceRepository.saveAll(newInvoices).stream().map(mapper::mapToInvoiceResponseDTO).toList();
    }

    public List<InvoiceResponseDTO> getInvoices(Long clientId) {
        var client = clientRepository.findById(clientId).orElseThrow();
        var invoices = invoiceRepository.findAllByClientAndPaid(client, false);
        return invoices.stream().map(mapper::mapToInvoiceResponseDTO).toList();
    }

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

    public TransactionResponseDTO createTransaction(Long purchaseId, TransactionFormDTO transactionFormDTO) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow();
        var newTransaction = new Transaction();
        newTransaction.setStatus(randomStatusGenerator());
        newTransaction.setAuthorizationCode(randomIntegerWithSize(10));
        newTransaction.setPurchase(purchase);
        return mapper.mapToTransactionResponseDTO(
                transactionRepository.save(newTransaction), purchaseId);
    }

    private StatusEnum randomStatusGenerator() {
        var random = new Random();
        StatusEnum[] values = StatusEnum.values();
        int index = random.nextInt(values.length);
        return values[index];
    }

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
