package com.example.smarty.service;

import com.example.smarty.config.SmartyProperties;
import com.example.smarty.dto.*;
import com.example.smarty.dto.usstreet.UsStreetAddressRequestDTO;
import com.example.smarty.dto.zipcode.ZipCodeLookupRequestDTO;
import com.example.smarty.dto.zipcode.ZipcodeLookupResponseDTO;
import com.example.smarty.dto.zipcode.ZipcodeLookupResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SmartyProperties smartyProperties;

    @Value("${smarty.auth.id}")
    private String authId;

    @Value("${smarty.auth.token}")
    private String authToken;

    protected final RestTemplate restTemplate = new RestTemplate();

    String buildUrl(String baseUrl) {
        return baseUrl + "?auth-id=" + authId + "&auth-token=" + authToken;
    }

    public <T> List<List<T>> chunkList(List<T> inputList, int chunkSize) {
        List<List<T>> chunks = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i += chunkSize) {
            chunks.add(inputList.subList(i, Math.min(i + chunkSize, inputList.size())));
        }
        return chunks;
    }
}
