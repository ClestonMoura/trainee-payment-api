package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.AddressDTO;
import br.com.flexpag.traineepaymentapi.dto.ViaCepResponseDTO;
import br.com.flexpag.traineepaymentapi.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final RestTemplate restTemplate;
    private final AddressMapper mapper;

    @Value("${appapplication.uri}")
    private String viaCepUrl;

    public AddressDTO getAddressByCep(String cep) {
        var viaCep = restTemplate.getForObject(viaCepUrl, ViaCepResponseDTO.class, Map.of("cep", cep));
        return mapper.mapToAddressDTO(viaCep);
    }

}
