package com.example.smarty.service;

import com.example.smarty.dto.international.IntlAddressRequestDTO;
import com.example.smarty.dto.international.IntlAddressResponseDTO;
import com.example.smarty.dto.international.IntlAddressResultDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class InternationalService extends SmartyService {


    public String verifyIntl(List<IntlAddressRequestDTO> requestList) {
        String url = buildUrl("https://international-street.api.smarty.com/verify");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<IntlAddressRequestDTO>> entity = new HttpEntity<>(requestList, headers);

        try {
            return restTemplate.postForObject(url, entity, String.class);
        } catch (HttpClientErrorException e) {
            return "Smarty API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }
    }


    public List<IntlAddressResultDTO> verifyIntlStreetClean(List<IntlAddressRequestDTO> lookups) {
        List<IntlAddressResultDTO> results = new ArrayList<>();
        List<List<IntlAddressRequestDTO>> chunks = chunkList(lookups, 100); // Smarty limit

        for (List<IntlAddressRequestDTO> chunk : chunks) {
            try {
                String url = buildUrl("https://international-street.api.smarty.com/verify");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                HttpEntity<List<IntlAddressRequestDTO>> request = new HttpEntity<>(chunk, headers);

                ResponseEntity<IntlAddressResponseDTO[]> response = restTemplate.exchange(
                        url, HttpMethod.POST, request, IntlAddressResponseDTO[].class
                );

                List<IntlAddressResultDTO> processed = Arrays.stream(Objects.requireNonNull(response.getBody()))
                        .filter(resp -> resp.getDeliveryLine1() != null)
                        .map(resp -> new IntlAddressResultDTO(
                                resp.getDeliveryLine1() + ", " + resp.getLastLine(),
                                resp.getComponents().getLocality(),
                                resp.getComponents().getPostalCode(),
                                resp.getComponents().getCountryIso3()
                        ))
                        .toList();

                results.addAll(processed);

            } catch (HttpClientErrorException e) {
                // Handle 413 or any error gracefully
                System.err.println("Batch failed: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());
            }
        }

        return results;
    }
}
