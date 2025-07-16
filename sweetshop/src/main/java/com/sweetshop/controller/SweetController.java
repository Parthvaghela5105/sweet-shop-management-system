package com.sweetshop.controller;

import com.sweetshop.dto.SweetDTO;
import com.sweetshop.services.SweetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sweets")
public class SweetController {

    private SweetService sweetService;
    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    //Adding sweet item into the database
    @PostMapping
    public ResponseEntity<SweetDTO> addSweet(@Valid @RequestBody SweetDTO sweetDTO){
        SweetDTO createdSweet = sweetService.addSweet(sweetDTO);
        return new ResponseEntity<>(createdSweet, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id){
        sweetService.deleteSweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
