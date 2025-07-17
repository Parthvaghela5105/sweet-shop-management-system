package com.sweetshop.controller;

import com.sweetshop.dto.PurchaseRequestDTO;
import com.sweetshop.dto.RestockRequestDTO;
import com.sweetshop.dto.SweetDTO;
import com.sweetshop.services.SweetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sweets")
@Tag(name = "Sweets", description = "Sweet Shop Inventory APIs")
public class SweetController {

    private SweetService sweetService;
    public SweetController(SweetService sweetService) {
        this.sweetService = sweetService;
    }

    //Adding sweet item into the database
    @Operation(summary = "Add a new sweet")
    @PostMapping
    public ResponseEntity<SweetDTO> addSweet(@Valid @RequestBody SweetDTO sweetDTO){
        SweetDTO createdSweet = sweetService.addSweet(sweetDTO);
        return new ResponseEntity<>(createdSweet, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete sweet by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSweet(@PathVariable Long id){
        sweetService.deleteSweet(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Get all sweets")
    @GetMapping
    public ResponseEntity<List<SweetDTO>> getAllSweets(){
        return new ResponseEntity<>(sweetService.getAllSweets(), HttpStatus.OK);
    }

    @Operation(summary = "Search sweets by name")
    @GetMapping("/search")
    public ResponseEntity<List<SweetDTO>> getSweetByName(@RequestParam String name){
        return new ResponseEntity<>(sweetService.getSweetByName(name), HttpStatus.OK);
    }

    @Operation(summary = "Search sweets by category")
    @GetMapping("/search-by-category")
    public ResponseEntity<List<SweetDTO>> getSweetByCategory(@RequestParam String category){
        return new ResponseEntity<>(sweetService.getSweetByCategory(category) , HttpStatus.OK);
    }

    @Operation(summary = "Search sweets by price range")
    @GetMapping("/search/range")
    public ResponseEntity<List<SweetDTO>> getSweetByPriceRange(@RequestParam double minPrice , @RequestParam double maxPrice)
    {
        return new ResponseEntity<>(sweetService.getSweetByPriceRange(minPrice , maxPrice) , HttpStatus.OK);
    }

    @Operation(summary = "Purchase a sweet (reduce stock)")
    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseSweet(@RequestBody PurchaseRequestDTO request) {
        String response = sweetService.purchaseSweet(request.getSweetId(), request.getQuantity());
        if (response.startsWith("Purchase successful")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @Operation(summary = "Restock a sweet (increase stock)")
    @PostMapping("/{id}/restock")
    public ResponseEntity<SweetDTO> restockSweet(@PathVariable Long id,
                                                 @RequestBody RestockRequestDTO requestDTO) {
        SweetDTO updated = sweetService.restockSweet(id, requestDTO.getQuantity());
        return ResponseEntity.ok(updated);
    }

}
