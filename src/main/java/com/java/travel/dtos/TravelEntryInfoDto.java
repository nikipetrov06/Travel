package com.java.travel.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelEntryInfoDto {

    @NotNull
    private int budgetPerCountry;

    @NotBlank
    private String startingCountry;

    @NotNull
    private int currentBudget;
}
