package br.com.flexpag.traineepaymentapi.mapper;

import br.com.flexpag.traineepaymentapi.dto.*;
import br.com.flexpag.traineepaymentapi.entity.Address;
import br.com.flexpag.traineepaymentapi.entity.Client;
import br.com.flexpag.traineepaymentapi.entity.Purchase;
import br.com.flexpag.traineepaymentapi.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityMapper {

    private final PasswordEncoder passwordEncoder;

    public AddressDTO mapToAddressDTO(ViaCepResponseDTO viaCepResponseDTO) {
        return new AddressDTO(
                viaCepResponseDTO.logradouro(),
                viaCepResponseDTO.complemento(),
                viaCepResponseDTO.bairro(),
                viaCepResponseDTO.localidade(),
                viaCepResponseDTO.uf()
        );
    }

    public Address mapToAddress(AddressDTO address) {
        return Address.builder()
                .publicPlace(address.publicPlace())
                .complement(address.complement())
                .neighborhood(address.neighborhood())
                .city(address.city())
                .state(address.state())
                .build();
    }

    public AddressDTO mapToAddressDTO(Address address) {
        return new AddressDTO(
                address.getPublicPlace(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState());
    }

    public Client mapToClient(ClientFormDTO request) {
        return Client.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .identity(request.identity())
                .contractType(request.contractType())
                .contractNumber(request.contractNumber())
                .address(mapToAddress(request.address()))
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
                mapToAddressDTO(client.getAddress()));
    }

    public User mapToUser(RegisterFormDTO registerFormDTO) {
        return User.builder()
                .username(registerFormDTO.username())
                .email(registerFormDTO.email())
                .password(passwordEncoder.encode(registerFormDTO.password()))
                .role(registerFormDTO.role())
                .build();
    }

    public AuthResponseDTO maptoAuthResponseDTO(User user, String jwtToken) {
        return new AuthResponseDTO(
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                jwtToken
        );
    }

    public Purchase mapToPurchase(PurchaseFormDTO request) {
        return Purchase.builder()
                .amount(request.amount())
                .invoiceAmount(request.invoiceAmount())
                .fee(request.fee())
                .build();
    }

    public PurchaseResponseDTO mapToPurchaseResponseDTO(Purchase purchase, Long clientId) {
        return new PurchaseResponseDTO(
                purchase.getId(),
                purchase.getCreatedOn(),
                purchase.getUpdatedOn(),
                purchase.getAmount(),
                purchase.getInvoiceAmount(),
                purchase.getFee(),
                clientId);
    }
}
