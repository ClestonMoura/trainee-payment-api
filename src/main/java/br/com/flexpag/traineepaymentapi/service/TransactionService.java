package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.TransactionFormDTO;
import br.com.flexpag.traineepaymentapi.dto.TransactionResponseDTO;
import br.com.flexpag.traineepaymentapi.entity.enums.StatusEnum;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import br.com.flexpag.traineepaymentapi.repository.PurchaseRepository;
import br.com.flexpag.traineepaymentapi.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final PurchaseRepository purchaseRepository;
    private final EntityMapper mapper;

    public TransactionResponseDTO addTransaction(TransactionFormDTO request, Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow();
        var newTransaction = mapper.mapToTransaction(request);
        newTransaction.setStatus(randomStatusGenerator());
        newTransaction.setAuthorizationCode(randomIntegerWithSize(10));
        newTransaction.setPurchase(purchase);
        return mapper.mapToTransactionResponseDTO(transactionRepository.save(newTransaction), purchaseId);
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

    public TransactionResponseDTO updateTransactionStatus(Long purchaseId, Long id) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow();
        var transaction = transactionRepository.findByPurchaseAndId(purchase, id).orElseThrow();
        transaction.setStatus(randomStatusGenerator());
        transaction.setAuthorizationCode(randomIntegerWithSize(10));
        return mapper.mapToTransactionResponseDTO(transaction, purchaseId);
    }
}
