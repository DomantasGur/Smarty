package com.example.smarty.dto.international;

import com.example.smarty.model.Components;
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


}
