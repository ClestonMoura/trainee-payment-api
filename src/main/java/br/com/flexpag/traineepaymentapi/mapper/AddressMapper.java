package br.com.flexpag.traineepaymentapi.mapper;

import br.com.flexpag.traineepaymentapi.dto.AddressResponseDTO;
import br.com.flexpag.traineepaymentapi.dto.ViaCepResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressResponseDTO mapToAddressResponseDTO(ViaCepResponseDTO viaCepResponseDTO) {
        return new AddressResponseDTO(
                viaCepResponseDTO.logradouro(),
                viaCepResponseDTO.complemento(),
                viaCepResponseDTO.bairro(),
                viaCepResponseDTO.localidade(),
                viaCepResponseDTO.uf()
        );
    }

}
