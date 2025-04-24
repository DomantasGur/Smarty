package com.example.smarty.controller;

import com.example.smarty.dto.*;
import com.example.smarty.dto.international.IntlAddressRequestDTO;
import com.example.smarty.dto.international.IntlAddressResultDTO;
import com.example.smarty.dto.usstreet.UsStreetAddressRequestDTO;
import com.example.smarty.dto.zipcode.ZipCodeLookupRequestDTO;
import com.example.smarty.dto.zipcode.ZipcodeLookupResultDTO;
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
}
