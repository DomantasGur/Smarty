package com.example.smarty.DTOs.IntlAddressDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntlAddressResponseDTO {

    private String deliveryLine1;
    private String lastLine;
    private Components components;
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Components {
        private String primaryNumber;
        private String streetName;
        private String locality;
        private String postalCode;
        private String countryIso3;
    }

}
