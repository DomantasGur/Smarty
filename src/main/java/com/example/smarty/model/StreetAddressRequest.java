package com.example.smarty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StreetAddressRequest {
    private String street;
    private String city;
    private String state;
    private String zipcode;
    private String candidates;
}
