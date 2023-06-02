package br.com.flexpag.traineepaymentapi.mapper;

import br.com.flexpag.traineepaymentapi.dto.*;
import br.com.flexpag.traineepaymentapi.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Classe de mapeamento de entidades e DTOs
 */
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

    public Invoice mapToInvoice(InvoiceFormDTO request) {
        return Invoice.builder()
                .dueDate(request.dueDate())
                .barcode(request.barcode())
                .amount(request.amount())
                .contractNumber(request.contractNumber())
                .build();
    }

    public InvoiceResponseDTO mapToInvoiceResponseDTO(Invoice invoice) {
        return new InvoiceResponseDTO(
                invoice.getId(),
                invoice.getCreatedOn(),
                invoice.getUpdatedOn(),
                invoice.getDueDate(),
                invoice.getBarcode(),
                invoice.getAmount(),
                invoice.getPaid(),
                invoice.getContractNumber());
    }

    public TransactionResponseDTO mapToTransactionResponseDTO(Transaction transaction, Long purchaseId) {
        return new TransactionResponseDTO(
                transaction.getId(),
                transaction.getCreatedOn(),
                transaction.getUpdatedOn(),
                transaction.getPaymentType(),
                transaction.getInstallments(),
                transaction.getStatus(),
                transaction.getAuthorizationCode(),
                purchaseId);
    }

    public PurchaseResponseDTO mapToPurchaseResponseDTO(Long clientId, Purchase purchase) {
        return new PurchaseResponseDTO(
                purchase.getId(),
                purchase.getCreatedOn(),
                purchase.getUpdatedOn(),
                purchase.getAmount(),
                purchase.getInvoiceAmount(),
                clientId,
                purchase.getInvoices().stream().map(this::mapToInvoiceResponseDTO).toList());
    }
}
