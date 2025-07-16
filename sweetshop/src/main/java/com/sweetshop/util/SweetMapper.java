package com.sweetshop.util;

import com.sweetshop.dto.SweetDTO;
import com.sweetshop.entities.Sweet;

public class SweetMapper {
    public static Sweet toEntity(SweetDTO dto) {
        return Sweet.builder()
                .id(dto.getId()) // optional for update
                .name(dto.getName())
                .category(dto.getCategory())
                .price(dto.getPrice())
                .quantity(dto.getQuantity())
                .build();
    }

    public static SweetDTO toDTO(Sweet entity) {
        return SweetDTO.builder()
                .id(entity.getId())
                .name(entity.getName())
                .category(entity.getCategory())
                .price(entity.getPrice())
                .quantity(entity.getQuantity())
                .build();
    }

}
