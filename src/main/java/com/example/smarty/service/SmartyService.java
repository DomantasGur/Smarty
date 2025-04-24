package com.example.smarty.service;

import com.example.smarty.DTOs.*;
import com.example.smarty.DTOs.IntlAddressDTOs.IntlAddressRequestDTO;
import com.example.smarty.DTOs.IntlAddressDTOs.IntlAddressResponseDTO;
import com.example.smarty.DTOs.IntlAddressDTOs.IntlAddressResultDTO;
import com.example.smarty.DTOs.ZipcodeDTOs.ZipCodeLookupRequestDTO;
import com.example.smarty.DTOs.ZipcodeDTOs.ZipcodeLookupResponseDTO;
import com.example.smarty.DTOs.ZipcodeDTOs.ZipcodeLookupResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class SmartyService {

    @Value("${smarty.auth.id}")
    private String authId;

    @Value("${smarty.auth.token}")
    private String authToken;

    private final RestTemplate restTemplate = new RestTemplate();

    private String buildUrl(String baseUrl) {
        return baseUrl + "?auth-id=" + authId + "&auth-token=" + authToken;
    }

    public <T> List<List<T>> chunkList(List<T> inputList, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i += chunkSize) {
            chunks.add(inputList.subList(i, Math.min(i + chunkSize, inputList.size())));
        }
        return chunks;
    }


    public String verifyUsStreet(List<UsStreetAddressRequestDTO> requestList) {
        String url = buildUrl("https://us-street.api.smarty.com/street-address");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<UsStreetAddressRequestDTO>> entity = new HttpEntity<>(requestList, headers);

        try {
            return restTemplate.postForObject(url, entity, String.class);
        } catch (HttpClientErrorException e) {
            return "Smarty API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }
    }



    public String lookupZipCode(List <ZipCodeLookupRequestDTO> requestList) {
        String url = buildUrl("https://us-zipcode.api.smarty.com/lookup");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ZipCodeLookupRequestDTO>> entity = new HttpEntity<>(requestList, headers);

        try {
            return restTemplate.postForObject(url, entity, String.class);
        }catch(HttpClientErrorException e){
            return "Smarty API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }
    }

    public String verifyIntl(List <IntlAddressRequestDTO> requestList) {
        String url = buildUrl("https://international-street.api.smarty.com/verify");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<IntlAddressRequestDTO>> entity = new HttpEntity<>(requestList, headers);

        try {
            return restTemplate.postForObject(url, entity, String.class);
        }catch(HttpClientErrorException e){
            return "Smarty API error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString();
        }
    }

    public String extractText(ExtractTextRequestDTO request) {
        String url = buildUrl("https://us-extract.api.smarty.com");
        HttpEntity<ExtractTextRequestDTO> entity = new HttpEntity<>(request);
        return restTemplate.postForObject(url, entity, String.class);
    }

    public List<IntlAddressResponseDTO> verifyIntlStreet(List<IntlAddressRequestDTO> lookups) {
        List<IntlAddressResponseDTO> allResults = new ArrayList<>();
        List<List<IntlAddressRequestDTO>> batches = chunkList(lookups, 100);

        for (List<IntlAddressRequestDTO> batch : batches) {
            String url = buildUrl("https://international-street.api.smarty.com/verify");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<List<IntlAddressRequestDTO>> request = new HttpEntity<>(batch, headers);

            ResponseEntity<IntlAddressResponseDTO[]> response = restTemplate.exchange(
                    url, HttpMethod.POST, request, IntlAddressResponseDTO[].class
            );

            allResults.addAll(Arrays.asList(Objects.requireNonNull(response.getBody())));
        }

        return allResults;
    }



    public List<IntlAddressResultDTO> verifyIntlStreetClean(List<IntlAddressRequestDTO> lookups) {
        List<IntlAddressResponseDTO> raw = verifyIntlStreet(lookups);

        return raw.stream()
                .map(resp -> new IntlAddressResultDTO(
                        resp.getDeliveryLine1() + ", " + resp.getLastLine(),
                        resp.getComponents().getLocality(),
                        resp.getComponents().getPostalCode(),
                        resp.getComponents().getCountryIso3()
                ))
                .toList();
    }

    public List<ZipcodeLookupResponseDTO> verifyUsZipCode(List<ZipCodeLookupRequestDTO> lookups) {
        String url = buildUrl("https://us-zipcode.api.smarty.com/lookup");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ZipCodeLookupRequestDTO>> request = new HttpEntity<>(lookups, headers);

        ResponseEntity<ZipcodeLookupResponseDTO[]> response = restTemplate.exchange(
                url, HttpMethod.POST, request, ZipcodeLookupResponseDTO[].class
        );

        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<ZipcodeLookupResultDTO> verifyUsZipCodeClean(List<ZipCodeLookupRequestDTO> lookups) {
        List<ZipcodeLookupResponseDTO> raw = verifyUsZipCode(lookups);

        return raw.stream()
                .map(resp -> {
                    var city = resp.getCities().isEmpty() ? null : resp.getCities().get(0);
                    var zip = resp.getZipcodes().isEmpty() ? null : resp.getZipcodes().get(0);
                    return new ZipcodeLookupResultDTO(
                            city != null ? city.getCity() : "N/A",
                            city != null ? city.getState() : "N/A",
                            zip != null ? zip.getCountyName() : "N/A"
                    );
                })
                .toList();
    }

}
