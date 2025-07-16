package com.sweetshop.services.impl;


import com.sweetshop.dto.SweetDTO;
import com.sweetshop.entities.Sweet;
import com.sweetshop.repositories.SweetRepository;
import com.sweetshop.services.SweetService;
import com.sweetshop.util.SweetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<SweetDTO> getAllSweets() {
        List<Sweet> sweetList = sweetRepository.findAll();
        return sweetList.stream().map(SweetMapper::toDTO).toList();
    }

    @Override
    public void deleteSweet(Long id) {
        if(!sweetRepository.existsById(id)){
            throw new RuntimeException("Sweet with id " + id + " does not exist");
        }
        sweetRepository.deleteById(id);
    }
}
