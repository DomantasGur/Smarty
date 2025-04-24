package com.example.smarty.DTOs.ZipcodeDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ZipcodeLookupResponseDTO {

    private List<City> cities;
    private List<Zipcode> zipcodes;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class City {
        private String city;
        private String state;
        private String mailableCity;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Zipcode {
        private String zipcode;
        private String countyName;
        private String stateAbbreviation;
    }

    // helper: get first city/zipcode string
    public String getCityStateString() {
        if (cities != null && !cities.isEmpty()) {
            City c = cities.get(0);
            return c.city + ", " + c.state + (c.mailableCity.equals("true") ? " (Mailable)" : "");
        }
        return "N/A";
    }
}
