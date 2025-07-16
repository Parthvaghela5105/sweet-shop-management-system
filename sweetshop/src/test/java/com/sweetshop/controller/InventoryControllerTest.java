package com.sweetshop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweetshop.dto.SweetDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InventoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPurchaseSweet_whenStockAvailable_thenDecreaseStockAndReturnSuccess() throws Exception {
        // First, add a sweet to the DB with sufficient quantity
        SweetDTO sweetDTO = SweetDTO.builder()
                .name("Rasgulla")
                .category("Milk-Based")
                .price(20.0)
                .quantity(100)
                .build();

        String sweetResponse = mockMvc.perform(post("/sweets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sweetDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extract sweetId from response (you can also use JsonNode if needed)
        Long sweetId = objectMapper.readTree(sweetResponse).get("id").asLong();

        // Now attempt to purchase 10 units
        PurchaseRequestDTO purchaseRequest = new PurchaseRequestDTO(sweetId, 10);

        mockMvc.perform(post("/sweets/purchase")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(purchaseRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string("Purchase successful! 10 units of Rasgulla purchased."));
    }

}
