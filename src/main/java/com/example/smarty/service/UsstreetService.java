package com.example.smarty.service;

import com.example.smarty.dto.usstreet.UsStreetAddressRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Service
public class UsstreetService extends SmartyService{


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
}
