package com.sweetshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RestockRequestDTO {
    @NotNull(message = "Sweet ID is required")
    private Long sweetId;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;
}
