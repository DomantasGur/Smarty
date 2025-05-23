package com.example.smarty.dto.usstreet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsStreetAddressRequestDTO {
    private String street;
    private String city;
    private String state;

}
