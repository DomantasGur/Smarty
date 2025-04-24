package com.example.smarty.dto.international;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IntlAddressRequestDTO {
    @NotBlank
    private String address1;
    @NotBlank
    private String locality;
    @NotBlank
    private String country;
    private String postalCode;
    private String administrativeArea;

}
