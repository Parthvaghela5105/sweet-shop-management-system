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

    @Override
    public List<SweetDTO> getSweetByName(String name) {
        if(sweetRepository.findByName(name) != null){
            List<Sweet> sweetList = sweetRepository.findByName(name);
            return sweetList.stream().map(SweetMapper::toDTO).toList();
        }
        return null;
    }

    @Override
    public List<SweetDTO> getSweetByCategory(String category) {
        if(sweetRepository.findByCategory(category) != null)
        {
            List<Sweet> sweetList = sweetRepository.findByCategory(category);
            return sweetList.stream().map(SweetMapper::toDTO).toList();
        }
        return null;
    }

    @Override
    public List<SweetDTO> getSweetByPriceRange(double minPrice, double maxPrice) {
        if(sweetRepository.findByPriceBetween(minPrice , maxPrice) != null)
        {
            List<Sweet> sweetList = sweetRepository.findByPriceBetween(minPrice , maxPrice);
            return sweetList.stream().map(SweetMapper::toDTO).toList();
        }
        return null;
    }

    @Override
    public String purchaseSweet(Long sweetId, int quantity) {
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found with ID: " + sweetId));

        if (sweet.getQuantity() < quantity) {
            return "Not enough stock available. Only " + sweet.getQuantity() + " units left.";
        }

        sweet.setQuantity(sweet.getQuantity() - quantity);
        sweetRepository.save(sweet);

        return "Purchase successful! " + quantity + " units of " + sweet.getName() + " purchased.";
    }
}
