package com.java.travel.services;

import com.java.travel.dtos.TravelEntryInfoDto;
import com.java.travel.entities.Country;
import com.java.travel.exceptions.BadRequestException;
import com.java.travel.repositories.CountryRepository;
import com.java.travel.services.impl.CountryServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerService {

    @InjectMocks
    private CountryServiceImpl countryService;

    @Mock
    private CountryRepository countryRepository;

    @Test
    public void test_travel_when_wrongCountryGiven() {
        //Given
        final TravelEntryInfoDto travelEntryInfoDto = mock(TravelEntryInfoDto.class);
        when(travelEntryInfoDto.getStartingCountry()).thenReturn("ASD");
        when(countryRepository.findByName(anyString())).thenThrow(new BadRequestException("Invalid start country given"));

        //When
        Exception exception = assertThrows(BadRequestException.class, () -> countryService.travel(travelEntryInfoDto));

        //Then
        String expected = "Invalid start country given";
        String actual = exception.getMessage();
        assertThat(actual, is(expected));
    }

    @Test
    public void test_travel_currentBudgetIsLow() {
        //Given
        final TravelEntryInfoDto travelEntryInfoDto = mock(TravelEntryInfoDto.class);
        final Country country = mock(Country.class);
        final List<Country> list = new ArrayList<>();
        list.add(new Country());
        when(country.getNeighbourCountries()).thenReturn(list);
        when(travelEntryInfoDto.getBudgetPerCountry()).thenReturn(100);
        when(travelEntryInfoDto.getCurrentBudget()).thenReturn(0);
        when(travelEntryInfoDto.getStartingCountry()).thenReturn("ASD");
        when(countryRepository.findByName(anyString())).thenReturn(Optional.of(country));

        //When
        final String actual = countryService.travel(travelEntryInfoDto);

        //Then
        final String expected = "Not enough money to travel";
        assertThat(actual, is(expected));
    }

    @Test
    public void test_travel() {
        //Given
        final TravelEntryInfoDto travelEntryInfoDto = mock(TravelEntryInfoDto.class);
        final Country country = mock(Country.class);
        final Country neighbourCountry = mock(Country.class);
        final List<Country> list = new ArrayList<>();
        list.add(neighbourCountry);
        when(country.getNeighbourCountries()).thenReturn(list);
        when(country.getName()).thenReturn("BG");
        when(neighbourCountry.getName()).thenReturn("TK");
        when(neighbourCountry.getCurrencyName()).thenReturn("TL");
        when(neighbourCountry.getEuroToCurrency()).thenReturn(7.76d);
        when(travelEntryInfoDto.getBudgetPerCountry()).thenReturn(100);
        when(travelEntryInfoDto.getCurrentBudget()).thenReturn(1000);
        when(travelEntryInfoDto.getStartingCountry()).thenReturn("ASD");
        when(countryRepository.findByName(anyString())).thenReturn(Optional.of(country));

        //When
        final String actual = countryService.travel(travelEntryInfoDto);

        //Then
        final String expected =  "Starting country is BG it has 1 neighbor countries [TK]" +
                " and User can travel through them 10 times." +
                " He will have 0.0 money left.For TK he will need to buy 7760.0 TL!";
        assertThat(actual, is(expected));
    }
}
