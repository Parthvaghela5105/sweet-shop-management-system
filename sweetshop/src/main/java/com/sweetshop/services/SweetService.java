package com.sweetshop.services;

import com.sweetshop.dto.SweetDTO;
import com.sweetshop.repositories.SweetRepository;

import java.util.List;

public interface SweetService {
    SweetDTO addSweet(SweetDTO sweetDTO);
    void deleteSweet(Long id);
    List<SweetDTO> getAllSweets();
    List<SweetDTO> getSweetByName(String name);
    List<SweetDTO> getSweetByCategory(String category);
}
