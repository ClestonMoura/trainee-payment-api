package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.InvoiceFormDTO;
import br.com.flexpag.traineepaymentapi.dto.InvoiceResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseFormDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseResponseDTO;
import br.com.flexpag.traineepaymentapi.entity.Invoice;
import br.com.flexpag.traineepaymentapi.entity.Purchase;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import br.com.flexpag.traineepaymentapi.repository.ClientRepository;
import br.com.flexpag.traineepaymentapi.repository.InvoiceRepository;
import br.com.flexpag.traineepaymentapi.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final InvoiceRepository invoiceRepository;
    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
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
        var client = clientRepository.findById(clientId).orElseThrow();
        var invoices = invoiceRepository.findAllByContractNumber(purchaseFormDTO.contractNumber());
        var invoiceAmount = invoices.stream().mapToDouble(Invoice::getAmount).sum();
        var fee = client.getFees(client.getContractType(), invoiceAmount);
        var amount = fee + invoiceAmount;
        var newpurchase = new Purchase();
        newpurchase.setFee(fee);
        newpurchase.setAmount(amount);
        newpurchase.setInvoiceAmount(invoiceAmount);
        newpurchase.setInvoices(invoices);
        return mapper.mapToPurchaseResponseDTO(purchaseRepository.save(newpurchase), clientId);


    }

}
