package br.com.flexpag.traineepaymentapi.service;

import br.com.flexpag.traineepaymentapi.dto.AddressDTO;
import br.com.flexpag.traineepaymentapi.dto.ViaCepResponseDTO;
import br.com.flexpag.traineepaymentapi.mapper.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * Classe de serviço para consulta do endereço
 */
@Service
@RequiredArgsConstructor
public class AddressService {

    private final RestTemplate restTemplate;
    private final EntityMapper mapper;

    @Value("${appapplication.uri}")
    private String viaCepUrl;

    /**
     * Métoodo que dispara uma requisição para o ViaCEP
     * @param cep Còdigo de Endereçamento Postal
     * @return Um DTO com os dados do endereço
     */
    public AddressDTO getAddressByCep(String cep) {
        var viaCep = restTemplate.getForObject(viaCepUrl, ViaCepResponseDTO.class, Map.of("cep", cep));
        return mapper.mapToAddressDTO(viaCep);
    }

}
