package com.example.smarty.DTOs.IntlAddressDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntlAddressRequestDTO {
    private String inputId;
    private String organization;
    private String address1;
    private String locality;
    private String administrativeArea;
    private String country;
    private String postalCode;

}
