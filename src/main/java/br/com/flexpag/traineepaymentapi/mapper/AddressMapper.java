package br.com.flexpag.traineepaymentapi.mapper;

import br.com.flexpag.traineepaymentapi.dto.AddressDTO;
import br.com.flexpag.traineepaymentapi.dto.ViaCepResponseDTO;
import br.com.flexpag.traineepaymentapi.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

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
}
