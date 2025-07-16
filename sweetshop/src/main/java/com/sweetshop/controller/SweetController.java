package com.sweetshop.controller;

import com.sweetshop.dto.SweetDTO;
import com.sweetshop.services.SweetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sweets")
public class SweetController {

    private SweetService sweetService;
    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    //Adding sweet item into the database
    @PostMapping
    public ResponseEntity<SweetDTO> addSweet(@RequestBody SweetDTO sweetDTO){
        SweetDTO createdSweet = sweetService.addSweet(sweetDTO);
        return new ResponseEntity<>(createdSweet, HttpStatus.CREATED);
    }

}
