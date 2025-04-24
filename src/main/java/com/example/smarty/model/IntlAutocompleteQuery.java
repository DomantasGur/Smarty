package com.example.smarty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IntlAutocompleteQuery {
    private String query;
    private String country;
}
