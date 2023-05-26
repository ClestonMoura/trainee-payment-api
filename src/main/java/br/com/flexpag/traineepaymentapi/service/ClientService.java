package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.ClientFormDTO;
import br.com.flexpag.traineepaymentapi.dto.ClientResponseDTO;
import br.com.flexpag.traineepaymentapi.mapper.ClientMapper;
import br.com.flexpag.traineepaymentapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final ClientMapper clientMapper;

    public ClientResponseDTO registerClient(ClientFormDTO request) {
        var newClient = repository.save(clientMapper.mapToClient(request));
        return clientMapper.mapToClientResponseDTO(newClient);
    }
}