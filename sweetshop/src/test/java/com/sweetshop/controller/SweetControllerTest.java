package com.sweetshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.dto.SweetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SweetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddSweet_withValidData_returnsCreatedSweet() throws Exception {
        SweetDTO sweetDTO = SweetDTO.builder()
                .name("Kaju Katli")
                .category("Nut-Based")
                .price(50.0)
                .quantity(20)
                .build();

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweetDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Kaju Katli")))
                .andExpect(jsonPath("$.category", is("Nut-Based")))
                .andExpect(jsonPath("$.price", is(50.0)))
                .andExpect(jsonPath("$.quantity", is(20)));
    }
}
