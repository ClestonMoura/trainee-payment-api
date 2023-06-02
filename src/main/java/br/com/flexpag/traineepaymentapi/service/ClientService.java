package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.ClientFormDTO;
import br.com.flexpag.traineepaymentapi.dto.ClientResponseDTO;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import br.com.flexpag.traineepaymentapi.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Classe de serviço para registro de cliente
 */
@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final EntityMapper clientMapper;

    /**
     * Registra um novo cliente na aplicação
     * @param request Formulário de registro de cliente
     * @return Um DTO do cliente criado
     */
    public ClientResponseDTO registerClient(ClientFormDTO request) {
        var newClient = repository.save(clientMapper.mapToClient(request));
        return clientMapper.mapToClientResponseDTO(newClient);
    }
}
