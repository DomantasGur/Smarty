package com.example.smarty.controller;

import com.example.smarty.dto.zipcode.ZipCodeLookupRequestDTO;
import com.example.smarty.dto.zipcode.ZipcodeLookupResultDTO;
import com.example.smarty.service.ZipcodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/zipcode")
public class ZipcodeController {

    private ZipcodeService zipcodeService;
    @PostMapping()
    public ResponseEntity<String> lookupZipCode(@RequestBody List<ZipCodeLookupRequestDTO> requestList) {
        String response = zipcodeService.lookupZipCode(requestList);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/us-zipcode")
    public ResponseEntity<List<ZipcodeLookupResultDTO>> cleanZip(@RequestBody List<ZipCodeLookupRequestDTO> lookups) {
        return ResponseEntity.ok(zipcodeService.verifyUsZipCodeClean(lookups));
    }

}
