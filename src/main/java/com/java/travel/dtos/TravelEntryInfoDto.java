package com.java.travel.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TravelEntryInfoDto {

    @NotNull
    @Min(0)
    private int budgetPerCountry;

    @NotBlank
    private String startingCountry;

    @NotNull
    @Min(0)
    private int currentBudget;
}
