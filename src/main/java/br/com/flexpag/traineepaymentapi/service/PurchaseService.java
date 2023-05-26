package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.PurchaseFormDTO;
import br.com.flexpag.traineepaymentapi.dto.PurchaseResponseDTO;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import br.com.flexpag.traineepaymentapi.repository.ClientRepository;
import br.com.flexpag.traineepaymentapi.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final ClientRepository clientRepository;
    private final EntityMapper mapper;

    public PurchaseResponseDTO addPurchase(PurchaseFormDTO request) {
        var client = clientRepository.findById(request.clientId()).orElseThrow();
        var newPucharse = mapper.mapToPurchase(request);
        newPucharse.setClient(client);
        return mapper.mapToPurchaseResponseDTO(purchaseRepository.save(newPucharse), request.clientId());
    }
}
