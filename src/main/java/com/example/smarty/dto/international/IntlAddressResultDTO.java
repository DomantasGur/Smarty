package com.example.smarty.dto.international;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntlAddressResultDTO {

    private String fullAddress;
    private String city;
    private String postalCode;
    private String country;
}
