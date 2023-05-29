package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.InvoiceFormDTO;
import br.com.flexpag.traineepaymentapi.dto.InvoiceResponseDTO;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import br.com.flexpag.traineepaymentapi.repository.InvoiceRepository;
import br.com.flexpag.traineepaymentapi.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PurchaseRepository purchaseRepository;
    private final EntityMapper mapper;

    public InvoiceResponseDTO addInvoice(InvoiceFormDTO request, Long purchaseId) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow();
        var newInvoice = mapper.mapToInvoice(request);
        newInvoice.setPurchase(purchase);
        return mapper.mapToInvoiceResponseDTO(invoiceRepository.save(newInvoice), purchaseId);
    }

    public InvoiceResponseDTO getInvoice(Long purchaseId, Long id) {
        var purchase = purchaseRepository.findById(purchaseId).orElseThrow();
        var invoice = invoiceRepository.findByPurchaseAndId(purchase, id).orElseThrow();
        return mapper.mapToInvoiceResponseDTO(invoice, purchaseId);
    }
}
