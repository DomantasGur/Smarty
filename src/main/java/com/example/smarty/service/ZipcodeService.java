package com.example.smarty.service;

import com.example.smarty.dto.zipcode.ZipCodeLookupRequestDTO;
import com.example.smarty.dto.zipcode.ZipcodeLookupResponseDTO;
import com.example.smarty.dto.zipcode.ZipcodeLookupResultDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class ZipcodeService extends SmartyService{

    public String lookupZipCode(List<ZipCodeLookupRequestDTO> requestList) {
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
