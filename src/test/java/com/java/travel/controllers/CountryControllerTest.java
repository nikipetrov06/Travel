package com.java.travel.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.travel.dtos.TravelEntryInfoDto;
import com.java.travel.services.impl.CountryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @InjectMocks
    private CountryController countryController;

    @Mock
    private CountryServiceImpl countryService;

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Before
    public void init() {

        mockMvc = standaloneSetup(countryController).build();
        mapper = new ObjectMapper();
    }

    @Test
    public void test_travel() throws Exception {
        when(countryService.travel(any(TravelEntryInfoDto.class))).thenReturn("ASD");

        mockMvc.perform(MockMvcRequestBuilders.get("/travel")
                .param("budgetPerCountry",  "100")
                .param("startingCountry", "BG")
                .param("currentBudget", "1000"))
                .andExpect(status().isOk());
    }
}
