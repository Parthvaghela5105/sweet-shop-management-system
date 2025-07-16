package com.sweetshop.services;

import com.sweetshop.dto.SweetDTO;
import com.sweetshop.repositories.SweetRepository;

public interface SweetService {
    SweetDTO addSweet(SweetDTO sweetDTO);
}
