package com.example.smarty.controller;

import com.example.smarty.dto.usstreet.UsStreetAddressRequestDTO;
import com.example.smarty.service.UsstreetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/us-street")
public class UsstreetController {
    private UsstreetService usstreetService;

    @PostMapping()
    public ResponseEntity<String> verifyUsStreet(@RequestBody List<UsStreetAddressRequestDTO> requestList) {
        String response = usstreetService.verifyUsStreet(requestList);
        return ResponseEntity.ok(response);
    }
}
