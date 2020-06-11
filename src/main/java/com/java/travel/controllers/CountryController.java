package com.java.travel.controllers;

import com.java.travel.dtos.TravelEntryInfoDto;
import com.java.travel.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class CountryController {

    private final CountryService countryService;

    @GetMapping("/travel")
    public String travel(@Valid final TravelEntryInfoDto travelEntryInfoDto){
        return countryService.travel(travelEntryInfoDto);
    }
}
