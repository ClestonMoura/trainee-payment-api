package br.com.flexpag.traineepaymentapi.mapper;

import br.com.flexpag.traineepaymentapi.dto.ClientFormDTO;
import br.com.flexpag.traineepaymentapi.dto.ClientResponseDTO;
import br.com.flexpag.traineepaymentapi.entity.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final AddressMapper mapper;

    public Client mapToClient(ClientFormDTO request) {
        return Client.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .identity(request.identity())
                .contractType(request.contractType())
                .contractNumber(request.contractNumber())
                .address(mapper.mapToAddress(request.address()))
                .build();
    }

    public ClientResponseDTO mapToClientResponseDTO(Client client) {
        return new ClientResponseDTO(
                client.getId(),
                client.getCreatedOn(),
                client.getUpdatedOn(),
                client.getFirstName(),
                client.getLastName(),
                client.getIdentity(),
                client.getContractType(),
                mapper.mapToAddressDTO(client.getAddress()));
    }
}
