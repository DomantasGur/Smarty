package com.example.smarty.controller;

import com.example.smarty.DTOs.*;
import com.example.smarty.DTOs.IntlAddressDTOs.IntlAddressRequestDTO;
import com.example.smarty.DTOs.IntlAddressDTOs.IntlAddressResultDTO;
import com.example.smarty.DTOs.ZipcodeDTOs.ZipCodeLookupRequestDTO;
import com.example.smarty.DTOs.ZipcodeDTOs.ZipcodeLookupResultDTO;
import com.example.smarty.service.SmartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/smarty")
public class SmartyController {

    @Autowired
    private SmartyService smartyService;


    @PostMapping("/us-street")
    public ResponseEntity<String> verifyUsStreet(@RequestBody List<UsStreetAddressRequestDTO> requestList) {
        String response = smartyService.verifyUsStreet(requestList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/zipcode")
    public ResponseEntity<String> lookupZipCode(@RequestBody List<ZipCodeLookupRequestDTO> requestList) {
        String response = smartyService.lookupZipCode(requestList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/intl")
    public ResponseEntity<String> verifyIntl(@RequestBody List<IntlAddressRequestDTO> requestList) {
        String response =  smartyService.verifyIntl(requestList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/extract")
    public String extractText(@RequestBody ExtractTextRequestDTO request) {
        return smartyService.extractText(request);
    }

    @PostMapping("/international-street")
    public ResponseEntity<List<IntlAddressResultDTO>> cleanIntl(@RequestBody List<IntlAddressRequestDTO> lookups) {
        return ResponseEntity.ok(smartyService.verifyIntlStreetClean(lookups));
    }

    @PostMapping("/us-zipcode")
    public ResponseEntity<List<ZipcodeLookupResultDTO>> cleanZip(@RequestBody List<ZipCodeLookupRequestDTO> lookups) {
        return ResponseEntity.ok(smartyService.verifyUsZipCodeClean(lookups));
    }

}
