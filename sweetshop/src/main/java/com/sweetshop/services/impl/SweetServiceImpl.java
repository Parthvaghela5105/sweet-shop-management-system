package com.sweetshop.services.impl;


import com.sweetshop.dto.SweetDTO;
import com.sweetshop.entities.Sweet;
import com.sweetshop.repositories.SweetRepository;
import com.sweetshop.services.SweetService;
import com.sweetshop.util.SweetMapper;
import org.springframework.stereotype.Service;

@Service
public class SweetServiceImpl implements SweetService {
    private SweetRepository sweetRepository;
    public SweetServiceImpl(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    @Override
    public SweetDTO addSweet(SweetDTO sweetDTO) {
        Sweet sweet = SweetMapper.toEntity(sweetDTO);
        sweetRepository.save(sweet);
        return SweetMapper.toDTO(sweet);
    }
}
