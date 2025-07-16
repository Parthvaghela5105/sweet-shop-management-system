package com.sweetshop.controller;

import com.sweetshop.dto.PurchaseRequestDTO;
import com.sweetshop.dto.SweetDTO;
import com.sweetshop.services.SweetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<SweetDTO>> getAllSweets(){
        return new ResponseEntity<>(sweetService.getAllSweets(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SweetDTO>> getSweetByName(@RequestParam String name){
        return new ResponseEntity<>(sweetService.getSweetByName(name), HttpStatus.OK);
    }

    @GetMapping("/search-by-category")
    public ResponseEntity<List<SweetDTO>> getSweetByCategory(@RequestParam String category){
        return new ResponseEntity<>(sweetService.getSweetByCategory(category) , HttpStatus.OK);
    }

    @GetMapping("/search/range")
    public ResponseEntity<List<SweetDTO>> getSweetByPriceRange(@RequestParam double minPrice , @RequestParam double maxPrice)
    {
        return new ResponseEntity<>(sweetService.getSweetByPriceRange(minPrice , maxPrice) , HttpStatus.OK);
    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseSweet(@RequestBody PurchaseRequestDTO request) {
        String response = sweetService.purchaseSweet(request.getSweetId(), request.getQuantity());
        if (response.startsWith("Purchase successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

}
