package com.sweetshop.services.impl;


import com.sweetshop.dto.SweetDTO;
import com.sweetshop.entities.Sweet;
import com.sweetshop.repositories.SweetRepository;
import com.sweetshop.services.SweetService;
import com.sweetshop.util.SweetMapper;
import org.springframework.stereotype.Service;

import java.util.List;

//Service implementation for handling business logic related to sweets.
//Supports operations like adding, deleting, searching, purchasing, and restocking sweets.
@Service
public class SweetServiceImpl implements SweetService {
    private SweetRepository sweetRepository;

    //Constructor-based injection of SweetRepository.
    public SweetServiceImpl(SweetRepository sweetRepository) {
        this.sweetRepository = sweetRepository;
    }

    //Adds a new sweet to the database.
    @Override
    public SweetDTO addSweet(SweetDTO sweetDTO) {
        Sweet sweet = SweetMapper.toEntity(sweetDTO);
        sweetRepository.save(sweet);
        return SweetMapper.toDTO(sweet);
    }

    //Retrieves all sweets from the database.
    @Override
    public List<SweetDTO> getAllSweets() {
        List<Sweet> sweetList = sweetRepository.findAll();
        return sweetList.stream().map(SweetMapper::toDTO).toList();
    }

    //Deletes a sweet by its ID.
    @Override
    public void deleteSweet(Long id) {
        if(!sweetRepository.existsById(id)){
            throw new RuntimeException("Sweet with id " + id + " does not exist");
        }
        sweetRepository.deleteById(id);
    }

    //Finds sweets by their name.
    //@param name the name to search
    //@return list of matching sweets as DTOs or null if not found
    @Override
    public List<SweetDTO> getSweetByName(String name) {
        if(sweetRepository.findByName(name) != null){
            List<Sweet> sweetList = sweetRepository.findByName(name);
            return sweetList.stream().map(SweetMapper::toDTO).toList();
        }
        return null;
    }

    //Finds sweets by category.
    //@param category the category to search
    //@return list of matching sweets as DTOs or null if not found
    @Override
    public List<SweetDTO> getSweetByCategory(String category) {
        if(sweetRepository.findByCategory(category) != null)
        {
            List<Sweet> sweetList = sweetRepository.findByCategory(category);
            return sweetList.stream().map(SweetMapper::toDTO).toList();
        }
        return null;
    }

    //Finds sweets whose price falls within a specified range.
    //@param minPrice minimum price
    //@param maxPrice maximum price
    //@return list of matching sweets as DTOs or null if not found
    @Override
    public List<SweetDTO> getSweetByPriceRange(double minPrice, double maxPrice) {
        if(sweetRepository.findByPriceBetween(minPrice , maxPrice) != null)
        {
            List<Sweet> sweetList = sweetRepository.findByPriceBetween(minPrice , maxPrice);
            return sweetList.stream().map(SweetMapper::toDTO).toList();
        }
        return null;
    }

    //@param sweetId the ID of the sweet
    //@param quantity the quantity to purchase
    //@return message indicating success or error due to insufficient stock
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

    //Restocks a sweet by increasing its stock quantity.
    //@param sweetId the ID of the sweet
    //@param quantity the quantity to add
    //@return the updated sweet as DTO
    @Override
    public SweetDTO restockSweet(Long sweetId, int quantity) {
        Sweet sweet = sweetRepository.findById(sweetId)
                .orElseThrow(() -> new RuntimeException("Sweet not found"));

        sweet.setQuantity(sweet.getQuantity() + quantity);
        Sweet updated = sweetRepository.save(sweet);
        return SweetMapper.toDTO(updated);
    }
}
