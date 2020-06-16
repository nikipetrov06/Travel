package com.java.travel.services.impl;

import com.java.travel.dtos.TravelEntryInfoDto;
import com.java.travel.entities.Country;
import com.java.travel.exceptions.BadRequestException;
import com.java.travel.repositories.CountryRepository;
import com.java.travel.services.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    @Override
    public String travel(final TravelEntryInfoDto travelEntryInfoDto) {
        final Country startCountry = countryRepository.findByName(travelEntryInfoDto.getStartingCountry())
                .orElseThrow(() -> new BadRequestException("Invalid start country given"));
        final int commonPriceForAllCountries =
                startCountry.getNeighbourCountries().size() * travelEntryInfoDto.getBudgetPerCountry();
        if (commonPriceForAllCountries > travelEntryInfoDto.getCurrentBudget()) {
            return "Not enough money to travel";
        }
        final int rounds = travelEntryInfoDto.getCurrentBudget() / commonPriceForAllCountries;
        final double remainder = travelEntryInfoDto.getCurrentBudget() % commonPriceForAllCountries;
        return buildFinalMessage(startCountry, travelEntryInfoDto.getBudgetPerCountry(), rounds, remainder).toString();
    }

    private StringBuilder buildFinalMessage(final Country startCountry,
                                            final int budgetPerCountry,
                                            final int rounds,
                                            final double remainder){
        final StringBuilder finalMessage = new StringBuilder();
        finalMessage.append("Starting country is ")
                .append(startCountry.getName())
                .append(" it has ")
                .append(startCountry.getNeighbourCountries().size())
                .append(" neighbor countries ")
                .append(startCountry.getNeighbourCountries()
                        .stream()
                        .map(Country::getName)
                        .collect(Collectors.toList())).append(" and User can travel through them ")
                .append(rounds).append(" times.")
                .append(" He will have ")
                .append(remainder)
                .append(" money left.");
        for (Country country : startCountry.getNeighbourCountries()) {
            finalMessage.append("For ")
                    .append(country.getName())
                    .append(" he will need to buy ")
                    .append((country.getEuroToCurrency() == null ?
                            1 : country.getEuroToCurrency()) * budgetPerCountry * rounds)
                    .append(" ")
                    .append(country.getCurrencyName())
                    .append("!");
        }
        return finalMessage;
    }
}
