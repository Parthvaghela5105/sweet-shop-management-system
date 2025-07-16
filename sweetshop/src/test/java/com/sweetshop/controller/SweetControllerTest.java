package com.sweetshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.dto.SweetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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

    @Test
    void testDeleteSweet_byId_returnsNoContent() throws Exception {
        // First, add a sweet so that we can delete it
        SweetDTO sweetDTO = SweetDTO.builder()
                .name("Gulab Jamun")
                .category("Milk-Based")
                .price(15.0)
                .quantity(10)
                .build();

        String response = mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweetDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract the ID from the response
        SweetDTO createdSweet = objectMapper.readValue(response, SweetDTO.class);
        Long sweetId = createdSweet.getId();

        // Now perform DELETE
        mockMvc.perform(delete("/sweets/{id}", sweetId))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllSweets_returnsListOfSweets() throws Exception {

        // Add first sweet
        SweetDTO sweet1 = SweetDTO.builder()
                .name("Kaju Katli")
                .category("Nut-Based")
                .price(50.0)
                .quantity(20)
                .build();

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet1)))
                .andExpect(status().isCreated());

        // Add second sweet
        SweetDTO sweet2 = SweetDTO.builder()
                .name("Gulab Jamun")
                .category("Milk-Based")
                .price(30.0)
                .quantity(15)
                .build();

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet2)))
                .andExpect(status().isCreated());

        // Perform GET request to fetch all sweets
        mockMvc.perform(get("/sweets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$[0].name").value("Kaju Katli"))
                .andExpect(jsonPath("$[1].name").value("Gulab Jamun"));
    }

    @Test
    void testSearchSweetsByName_returnsMatchingSweets() throws Exception {

        // Add test sweets
        SweetDTO sweet1 = SweetDTO.builder()
                .name("Gulab Jamun")
                .category("Milk-Based")
                .price(30.0)
                .quantity(10)
                .build();

        SweetDTO sweet2 = SweetDTO.builder()
                .name("Kaju Katli")
                .category("Nut-Based")
                .price(50.0)
                .quantity(20)
                .build();

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet2)))
                .andExpect(status().isCreated());

        // Perform the search by name = Gulab
        mockMvc.perform(get("/sweets/search")
                    .param("name", "Gulab Jamun"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("Gulab Jamun"));
    }

    @Test
    void testSearchSweetsByCategory_returnsMatchingSweets() throws Exception {
        // Add test sweets
        SweetDTO sweet1 = SweetDTO.builder()
                .name("Gulab Jamun")
                .category("Milk-Based")
                .price(30.0)
                .quantity(15)
                .build();

        SweetDTO sweet2 = SweetDTO.builder()
                .name("Rasgulla")
                .category("Milk-Based")
                .price(25.0)
                .quantity(10)
                .build();

        SweetDTO sweet3 = SweetDTO.builder()
                .name("Kaju Katli")
                .category("Nut-Based")
                .price(50.0)
                .quantity(20)
                .build();

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet2)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet3)))
                .andExpect(status().isCreated());

        // Perform the category-based search
        mockMvc.perform(get("/sweets/search-by-category")
                        .param("category", "Milk-Based"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(greaterThanOrEqualTo(2)))
                .andExpect(jsonPath("$[0].category").value("Milk-Based"))
                .andExpect(jsonPath("$[1].category").value("Milk-Based"));
    }

    @Test
    void testSearchSweetsByPriceRange() throws Exception {
        SweetDTO sweet1 = SweetDTO.builder().name("Kaju Katli").category("Nut-Based").price(45.0).quantity(10).build();
        SweetDTO sweet2 = SweetDTO.builder().name("Gulab Jamun").category("Milk-Based").price(30.0).quantity(15).build();
        SweetDTO sweet3 = SweetDTO.builder().name("Ladoo").category("Gram-Based").price(60.0).quantity(5).build();

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet1)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet2)))
                .andExpect(status().isCreated());

        mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweet3)))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/sweets/search/range")
                        .param("min", "25")
                        .param("max", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").exists())
                .andExpect(jsonPath("$[1].name").exists());
    }


}
