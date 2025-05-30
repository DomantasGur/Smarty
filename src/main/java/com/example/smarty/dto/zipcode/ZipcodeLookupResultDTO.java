package com.example.smarty.dto.zipcode;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ZipcodeLookupResultDTO {
    private String city;
    private String state;
    private String county;
}
