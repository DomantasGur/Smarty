package com.example.smarty.controller;

import com.example.smarty.dto.international.IntlAddressRequestDTO;
import com.example.smarty.dto.international.IntlAddressResultDTO;
import com.example.smarty.service.InternationalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("international")
public class InternationalController {

    @Autowired
    private InternationalService internationalService;

    @PostMapping("/intl")
    public ResponseEntity<String> verifyIntl(@RequestBody List<IntlAddressRequestDTO> requestList) {
        String response =  internationalService.verifyIntl(requestList);
        return ResponseEntity.ok(response);
    }
    @PostMapping("/street")
    public ResponseEntity<List<IntlAddressResultDTO>> cleanIntl(@RequestBody List<IntlAddressRequestDTO> lookups) {
        return ResponseEntity.ok(internationalService.verifyIntlStreetClean(lookups));
    }
}
